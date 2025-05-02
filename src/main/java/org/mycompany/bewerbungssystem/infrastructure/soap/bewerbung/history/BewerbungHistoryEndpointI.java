package org.mycompany.bewerbungssystem.infrastructure.soap.bewerbung.history;

import java.util.List;

import org.mycompany.bewerbungssystem.BewerbungHistoryDTO;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

//@WebService(targetNamespace = "http://mycompany.com/bewerbungssystem")
//@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
@WebService
public interface BewerbungHistoryEndpointI {

	@WebMethod
	List<BewerbungHistoryDTO> historyAnsehen(@WebParam(name = "id") Long id);

}
