package org.mycompany.bewerbungssystem.api.exception;
import jakarta.xml.ws.WebFault;

@WebFault(
    name = "InvalidBewerbungFault",
    targetNamespace = "http://mycompany.com/bewerbungssystem"
)
public class InvalidBewerbungException extends Exception {
    
	private static final long serialVersionUID = 1L;

	private InvalidBewerbungFault faultInfo;

    public InvalidBewerbungException(String message) {
        super(message);
        this.faultInfo = new InvalidBewerbungFault(message);
    }
    
	public InvalidBewerbungFault getFaultInfo() {
		return faultInfo;
	}

	public void setFaultInfo(InvalidBewerbungFault faultInfo) {
		this.faultInfo = faultInfo;
	}

}


class InvalidBewerbungFault {
    private String errorMessage;
    
	public InvalidBewerbungFault(String errorMessage) {
		this.errorMessage = errorMessage;
    }
    
}