package org.mycompany.bewerbungssystem.infrastructure.soap.bewerbung.history;

import java.util.List;

import org.mycompany.bewerbungssystem.BewerbungHistoryDTO;
import org.mycompany.bewerbungssystem.application.service.BewerbungHistoryService;

import jakarta.inject.Inject;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

//@WebService(endpointInterface = "com.mycompany.bewerbungssystem.infrastructure.soap.bewerbung.BewerbungEndpointI", serviceName = "BewerbungService", portName = "BewerbungPort")
//@BindingType(jakarta.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)

@WebService(serviceName = "BewerbungHistoryService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class BewerbungHistoryEndpointImpl implements BewerbungHistoryEndpointI {

	@Inject
	BewerbungHistoryService service;

	@Override
	public List<BewerbungHistoryDTO> historyAnsehen(@WebParam Long id) {

		return service.getHistoryByBewerbungId(id);
	}

}