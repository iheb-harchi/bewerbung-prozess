package org.mycompany.bewerbungssystem.infrastructure.soap.bewerbung;

import org.mycompany.bewerbungssystem.api.exception.InvalidBewerbungException;
import org.mycompany.bewerbungssystem.application.service.BewerbungService;

import com.mycompany.bewerbung.BewerbungDTO;

import jakarta.inject.Inject;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

//@WebService(endpointInterface = "com.mycompany.bewerbungssystem.infrastructure.soap.bewerbung.BewerbungEndpointI", serviceName = "BewerbungService", portName = "BewerbungPort")
//@BindingType(jakarta.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)

@WebService(serviceName = "BewerbungService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class BewerbungEndpointImpl implements BewerbungEndpointI {

	@Inject
	private BewerbungService bewerbungService;

	@Override
	public SubmitBewerbungResponse submitBewerbung(BewerbungDTO bewerbungDTO) {

		try {
			BewerbungDTO saved = bewerbungService.bewerbungAnlegen(bewerbungDTO);
			return new SubmitBewerbungResponse("Bewerbung erfolgreich eingereicht", saved.getId(),
					saved.getStatus(), true);
		} catch (InvalidBewerbungException e) {

			return new SubmitBewerbungResponse("Fehlerhafte Bewerbung: " + e.getMessage(), null, null, false);
		}
	}
}