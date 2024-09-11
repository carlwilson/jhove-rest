/**
 *
 */
package org.openpreservation.jhove.rest.resources;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.openpreservation.jhove.ReleaseDetails;
import org.openpreservation.jhove.modules.ModuleDetails;
import org.openpreservation.jhove.modules.ValidationReport;

import edu.harvard.hul.ois.jhove.App;
import edu.harvard.hul.ois.jhove.JhoveBase;
import edu.harvard.hul.ois.jhove.JhoveException;
import edu.harvard.hul.ois.jhove.Module;
import edu.harvard.hul.ois.jhove.RepInfo;

/**
 * The REST resource definition for byte stream identification services, these
 * are JERSEY REST services and it's the annotations that perform the magic of
 * handling content types and serialisation.
 *
 * @author <a href="mailto:carl@openpreservation.org">Carl Wilson</a>.
 *         </p>
 */
public class JhoveResources {
	private static final String SHA1_NAME = "SHA-1"; //$NON-NLS-1$

	private static final JhoveBase initBase() {
		try {
			JhoveBase base = new JhoveBase();
			base.init("jhove.conf", null);
			return base;
		} catch (JhoveException e) {
			throw new IllegalStateException("Couldn't initialise JHOVE base", e);
		}
	}

	private static final Map<String, ModuleDetails> getModuleDetails(List<Module> modules) {
		Map<String, ModuleDetails> retVal = new HashMap<>();
		for (Module module : modules) {
			retVal.put(module.getName(), ModuleDetails.fromModule(module));
		}
		return Collections.unmodifiableMap(retVal);
	}

	private static final JhoveBase jhoveBase = initBase();
	private static final Map<String, ModuleDetails> modules = getModuleDetails(jhoveBase.getModuleList());
	private static final App app = App.newAppWithName("JHOVE");

	/**
	 * Default public constructor required by Jersey / Dropwizard
	 */
	public JhoveResources() {
		super();
	}

	/**
	 * @param uploadedInputStream      InputStream for the uploaded file
	 * @param contentDispositionHeader extra info about the uploaded file, currently
	 *                                 unused.
	 * @return the {@link org.openpreservation.bytestream.ByteStreamId} of the
	 *         uploaded file's byte stream serialised according to requested content
	 *         type.
	 */
	@GET
	@javax.ws.rs.Path("/")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public static ReleaseDetails jhoveApp() {
		return ReleaseDetails.getInstance();
	}

	/**
	 * @param uploadedInputStream      InputStream for the uploaded file
	 * @param contentDispositionHeader extra info about the uploaded file, currently
	 *                                 unused.
	 * @return the {@link org.openpreservation.bytestream.ByteStreamId} of the
	 *         uploaded file's byte stream serialised according to requested content
	 *         type.
	 */
	@GET
	@javax.ws.rs.Path("/modules")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public static Collection<ModuleDetails> modules() {
		return modules.values();
	}

	/**
	 * @param uploadedInputStream      InputStream for the uploaded file
	 * @param contentDispositionHeader extra info about the uploaded file, currently
	 *                                 unused.
	 * @return the {@link org.openpreservation.bytestream.ByteStreamId} of the
	 *         uploaded file's byte stream serialised according to requested content
	 *         type.
	 */
	@GET
	@javax.ws.rs.Path("/modules/{module_name}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public static ModuleDetails module(@PathParam("module_name") String moduleName) {
		System.err.println("MODULES");
		Module module = jhoveBase.getModuleMap().get(moduleName.toLowerCase());
		if (module == null) {
			throw new NotFoundException("Could not find module with name: " + moduleName);
		}
		return ModuleDetails.fromModule(module);
	}

	/**
	 * @param uploadedInputStream      InputStream for the uploaded file
	 * @param contentDispositionHeader extra info about the uploaded file, currently
	 *                                 unused.
	 * @return the {@link org.openpreservation.bytestream.ByteStreamId} of the
	 *         uploaded file's byte stream serialised according to requested content
	 *         type.
	 * @throws Exception
	 */
	@POST
	@javax.ws.rs.Path("/validate")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public static ValidationReport validate(@FormDataParam("module") String moduleName,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") final FormDataContentDisposition contentDispositionHeader) {
		if (!modules.containsKey(moduleName)) {
			throw new NotFoundException("Could not find module with name: " + moduleName);
		}
		MessageDigest sha1 = getDigest();
		DigestInputStream dis = new DigestInputStream(uploadedInputStream, sha1);
		Path tempFile;
		try {
			tempFile = inputToTempPath(dis);
		} catch (IOException e) {
			throw new ProcessingException("Server error serialising file: " + contentDispositionHeader.getFileName(),
					e);
		}
		Module module = jhoveBase.getModule(moduleName);
		RepInfo repInfo = new RepInfo(contentDispositionHeader.getFileName());
		try {
			if (!jhoveBase.processFile(app, module, false, tempFile.toFile(), repInfo)) {
				throw new ProcessingException(
						"JHOVE Processing aborted for file: " + contentDispositionHeader.getFileName());
			}
		} catch (Exception e) {
			throw new ProcessingException("JHOVE Exception for file: " + contentDispositionHeader.getFileName(), e);
		}
		return new ValidationReport(repInfo);
	}

	private static MessageDigest getDigest() {
		try {
			return MessageDigest.getInstance(SHA1_NAME);
		} catch (NoSuchAlgorithmException nsaExcep) {
			// If this happens the Java Digest algorithms aren't present, a
			// faulty Java install??
			throw new IllegalStateException(
					"No digest algorithm implementation for " + SHA1_NAME + ", check you Java installation.", nsaExcep); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	private static Path inputToTempPath(InputStream is) throws IOException {
		Path temp = Files.createTempFile("jhove-", "-up");
		try (OutputStream os = new FileOutputStream(temp.toFile())) {
			byte[] buff = new byte[4096];
			int length;
			while ((length = is.read(buff)) > 0) {
				os.write(buff, 0, length);
			}
		}
		return temp;
	}
}
