package org.openpreservation.jhove.rest.exceptions;

/**
 * @author <a href="mailto:carl@openpreservation.org">Carl Wilson</a>
 *         <a href="https://github.com/carlwilson">carlwilson AT github</a>
 *
 * @version 0.1
 *
 *          Created 6 Aug 2018:15:06:28
 */

public class HttpJhoveException extends Throwable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1873909880167460213L;
	public final int code;

	/**
	 *
	 */
	public HttpJhoveException() {
		this(500);
	}

	/**
	 *
	 */
	public HttpJhoveException(final int code) {
		this(code, "An error occurred while proecessing your request.");
	}

	/**
	 * @param arg0
	 */
	public HttpJhoveException(final int code, final String message) {
		this(code, message, null);
	}

	/**
	 * @param arg0
	 */
	public HttpJhoveException(final int code, final Throwable cause) {
		this(code, cause.getMessage(), cause);
	}

	/**
	 * @param arg0
	 */
	public HttpJhoveException(final int code, final String message,
			final Throwable cause) {
		super(message, cause);
		this.code = code;
	}
}
