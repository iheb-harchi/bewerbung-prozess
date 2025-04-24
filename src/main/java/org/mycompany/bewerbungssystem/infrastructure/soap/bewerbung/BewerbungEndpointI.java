package org.mycompany.bewerbungssystem.infrastructure.soap.bewerbung;

import org.mycompany.bewerbungssystem.api.exception.InvalidBewerbungException;

import com.mycompany.bewerbung.BewerbungDTO;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;

//@WebService(targetNamespace = "http://mycompany.com/bewerbungssystem")
//@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
@WebService
public interface BewerbungEndpointI {

	@WebMethod(operationName = "SubmitBewerbung")
	@WebResult(name = "SubmitBewerbungResponse")
	SubmitBewerbungResponse submitBewerbung(@WebParam(name = "Bewerbung") BewerbungDTO bewerbungDTO)
			throws InvalidBewerbungException;

}
