package org.mycompany.bewerbungssystem.infrastructure.soap.bewerber;


import java.util.List;

import org.mycompany.bewerbungssystem.api.exception.ResourceNotFoundException;
import org.mycompany.bewerbungssystem.application.service.BewerberService;

import com.mycompany.bewerber.BewerberDTO;

import jakarta.inject.Inject;
import jakarta.jws.WebService;

/**
 * SOAP WebService Endpoint für Bewerberoperationen. Stellt Methoden zur
 * Verwaltung von Bewerberdaten bereit.
 */
@WebService(
	    serviceName = "BewerberService",
	    portName = "BewerberPort",
	    targetNamespace = "http://mycompany.com/bewerber"
	)
	public class BewerberEndpointImpl implements BewerberEndpointI {
		@Inject
		private BewerberService bewerberService;


		@Override
	    public List<BewerberDTO> getAllBewerber() {
	        return bewerberService.getAllBewerber();
	    }

	    @Override
		public BewerberDTO getBewerberById(Long bewerberId)
	            throws ResourceNotFoundException {
	        return bewerberService.getBewerberById(bewerberId);
	    }

	    @Override
		public void deleteBewerber(Long bewerberId)
	            throws ResourceNotFoundException {
	        bewerberService.deleteBewerberById(bewerberId);
	    }
}