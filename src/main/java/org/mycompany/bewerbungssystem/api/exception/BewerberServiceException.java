package org.mycompany.bewerbungssystem.api.exception;

public class BewerberServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public BewerberServiceException(String message) {
		super(message);
	}

	public BewerberServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}