package org.mycompany.bewerbungssystem.infrastructure.soap.bewerbung;
import com.mycompany.bewerbung.BewerbungStatus;

// src/main/java/com/mycompany/bewerbungssystem/api/dto/SubmitBewerbungResponse.java
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SubmitBewerbungResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class SubmitBewerbungResponse {
    
    private String message;
    private Long bewerbungId;
	private BewerbungStatus status;
	private boolean success;
    

    public SubmitBewerbungResponse() {}
    
	public SubmitBewerbungResponse(String message, Long bewerbungId, BewerbungStatus status, boolean success) {
        this.message = message;
        this.bewerbungId = bewerbungId;
        this.status = status;
		this.success = success;
    }
    
}