package org.mycompany.bewerbungssystem.infrastructure.soap.bewerbung;

import java.util.List;

import org.mycompany.bewerbung.BewerbungFilter;
import org.mycompany.bewerbungssystem.application.service.BewerbungService;
import org.mycompany.bewerbungssystem.application.validation.ValidationResult;
import org.mycompany.bewerbungssystem.application.validation.XsdValidator;
import org.mycompany.common.BewerbungStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycompany.bewerbung.BewerbungDTO;

import jakarta.inject.Inject;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

//@WebService(endpointInterface = "com.mycompany.bewerbungssystem.infrastructure.soap.bewerbung.BewerbungEndpointI", serviceName = "BewerbungService", portName = "BewerbungPort")
//@BindingType(jakarta.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)

@WebService(serviceName = "BewerbungService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class BewerbungEndpointImpl implements BewerbungEndpointI {

	private static final Logger LOG = LoggerFactory.getLogger(BewerbungEndpointImpl.class);
	private static final String XSD_PATH = "xsd/bewerbung.xsd";

	@Inject
	private BewerbungService bewerbungService;

	@Override
	public SubmitBewerbungResponse submitBewerbung(BewerbungDTO bewerbungDTO) {
		try {
			LOG.info("Received new application submission");

			// Step 1: XSD Validation
			ValidationResult validation = XsdValidator.validate(bewerbungDTO,
					XSD_PATH);

			if (!validation.isValid()) {
				LOG.warn("Validation failed: {}", validation.getErrors());
				return SubmitBewerbungResponse.validationError("Invalid application data", validation.getErrors());
			}

			// Step 2: Business Processing
			BewerbungDTO saved = bewerbungService.createBewerbung(bewerbungDTO);
			LOG.info("Application submitted successfully, ID: {}", saved.getId());

			return SubmitBewerbungResponse.success("Application submitted successfully", saved.getId(),
					saved.getStatus());

		} catch (Exception e) {
			LOG.error("Application submission failed", e);
			return SubmitBewerbungResponse.businessError("Application processing failed: " + e.getMessage());
		}
	}

	@Override
	public BewerbungDTO bewerbungAnsehen(Long id) {
		return bewerbungService.getBewerbung(id);
	}

	@Override
	public void aktualisiereStatus(Long id, BewerbungStatus newStatus) {
		bewerbungService.updateStatus(id, newStatus);

	}

	@Override
	public void deleteBewerbung(Long id) {
		bewerbungService.deleteBewerbung(id);
	}

	@Override
	public List<BewerbungDTO> holeAlleBewerbungen() {
		return bewerbungService.getAllBewerbungen();
	}

	@Override
	public List<BewerbungDTO> filterBewerbungen(BewerbungFilter bewerbungFilter) {
		return bewerbungService.filterBewerbungen(bewerbungFilter);
	}

}