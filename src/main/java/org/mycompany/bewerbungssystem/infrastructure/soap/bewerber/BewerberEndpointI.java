package org.mycompany.bewerbungssystem.infrastructure.soap.bewerber;

import java.util.List;

import com.mycompany.bewerber.BewerberDTO;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService
public interface BewerberEndpointI {

	@WebMethod
	List<BewerberDTO> getAllBewerber();

	@WebMethod
	BewerberDTO getBewerberById(@WebParam(name = "id") Long id);

	@WebMethod
	void deleteBewerber(@WebParam(name = "id") Long id);

}
