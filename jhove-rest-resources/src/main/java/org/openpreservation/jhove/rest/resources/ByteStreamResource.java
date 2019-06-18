/**
 * 
 */
package org.openpreservation.jhove.rest.resources;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.openpreservation.bytestreams.ByteStreamId;
import org.openpreservation.bytestreams.ByteStreams;

/**
 * The REST resource definition for byte stream identification services, these
 * are JERSEY REST services and it's the annotations that perform the magic of
 * handling content types and serialisation.
 * 
 * @author <a href="mailto:carl@openpreservation.org">Carl Wilson</a>.
 *         </p>
 */
public class ByteStreamResource {
	/**
	 * Default public constructor required by Jersey / Dropwizard
	 */
	public ByteStreamResource() {
		super();
	}

	/**
	 * @param uploadedInputStream
	 *            InputStream for the uploaded file
	 * @param contentDispositionHeader
	 *            extra info about the uploaded file, currently unused.
	 * @return the {@link org.verapdf.pdfa.metadata.bytestream.ByteStreamId} of
	 *         the uploaded file's byte stream serialised according to requested
	 *         content type.
	 */
	@POST
	@Path("/sha1")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ MediaType.APPLICATION_JSON })
	public static ByteStreamId getSha1(
			@FormDataParam("file") InputStream uploadedInputStream,
			@SuppressWarnings("unused") @FormDataParam("file") final FormDataContentDisposition contentDispositionHeader) {
		try {
			ByteStreamId id = ByteStreams.idFromStream(uploadedInputStream);
			uploadedInputStream.close();
			return id;// return
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ByteStreams.nullByteStreamId();
	}

	/**
	 * @return the {@link org.openpreservation.bytestreams.ByteStreamId} of
	 *         an empty (0 byte) byte stream serialised according to requested
	 *         content type.
	 */
	@GET
	@Path("/sha1/null")
	@Produces({ MediaType.APPLICATION_JSON })
	public static ByteStreamId getEmptySha1() {
		return ByteStreams.nullByteStreamId();
	}

	/**
	 * @param uploadedInputStream
	 *            InputStream for the uploaded file
	 * @param contentDispositionHeader
	 *            extra info about the uploaded file, currently unused.
	 * @return the {@link org.glassfish.jersey.media.multipart.FormDataContentDisposition} for
	 *         the uploaded file for testing>
	 */
	@POST
	@Path("/form-data")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ MediaType.APPLICATION_JSON })
	public static FormDataContentDisposition getFormDataInfo(
			@SuppressWarnings("unused") @FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") final FormDataContentDisposition contentDispositionHeader) {
		return contentDispositionHeader;
	}
}
