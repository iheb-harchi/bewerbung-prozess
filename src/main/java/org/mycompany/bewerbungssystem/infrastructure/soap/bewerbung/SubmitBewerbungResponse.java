package org.mycompany.bewerbungssystem.infrastructure.soap.bewerbung;

import java.util.Collections;
import java.util.List;

import org.mycompany.common.BewerbungStatus;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "SubmitBewerbungResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "status", "bewerbungId", "message", "success", "validationErrors" })
public class SubmitBewerbungResponse {

	@XmlElement(required = true)
    private String message;

	@XmlElement(name = "bewerbungId")
    private Long bewerbungId;

	@XmlElement(required = true)
    private BewerbungStatus status;

	@XmlElement(required = true)
    private boolean success;

	@XmlElementWrapper(name = "validationErrors")
	@XmlElement(name = "error")
	private List<String> validationErrors;

    public SubmitBewerbungResponse() {}

	// Factory methods for success/error cases
	public static SubmitBewerbungResponse success(String message, Long bewerbungId, BewerbungStatus status) {
		return new SubmitBewerbungResponse(message, bewerbungId, status, true, null);
	}

	public static SubmitBewerbungResponse validationError(String message, List<String> validationErrors) {
		return new SubmitBewerbungResponse(message, null, null, false, validationErrors);
	}

	public static SubmitBewerbungResponse businessError(String message) {
		return new SubmitBewerbungResponse(message, null, null, false, null);
	}

	private SubmitBewerbungResponse(String message, Long bewerbungId, BewerbungStatus status, boolean success,
			List<String> validationErrors) {
        this.message = message;
        this.bewerbungId = bewerbungId;
        this.status = status;
        this.success = success;
		this.validationErrors = validationErrors != null ? Collections.unmodifiableList(validationErrors)
				: Collections.emptyList();
    }

	// Getters with null checks
	public List<String> getValidationErrors() {
		return validationErrors != null ? validationErrors : Collections.emptyList();
	}
}