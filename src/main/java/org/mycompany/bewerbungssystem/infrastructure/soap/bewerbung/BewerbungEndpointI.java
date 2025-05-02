package org.mycompany.bewerbungssystem.infrastructure.soap.bewerbung;

import java.util.List;

import org.mycompany.bewerbung.BewerbungFilter;
import org.mycompany.bewerbungssystem.api.exception.InvalidBewerbungException;
import org.mycompany.common.BewerbungStatus;

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

	@WebMethod
	List<BewerbungDTO> holeAlleBewerbungen();

	@WebMethod
	List<BewerbungDTO> filterBewerbungen(@WebParam(name = "filter") BewerbungFilter bewerbungFilter);

	@WebMethod
	BewerbungDTO bewerbungAnsehen(@WebParam(name = "id") Long id);

	@WebMethod
	void aktualisiereStatus(@WebParam(name = "id") Long id,
			@WebParam(name = "status") BewerbungStatus newStatus);

	@WebMethod
	void deleteBewerbung(@WebParam(name = "id") Long id);


}
