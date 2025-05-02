package org.mycompany.bewerbungssystem.infrastructure.soap.job;

import java.util.List;

import org.mycompany.bewerbungssystem.api.exception.InvalidBewerbungException;

import com.mycompany.job.JobDTO;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

//@WebService(targetNamespace = "http://mycompany.com/bewerbungssystem")
//@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
@WebService
public interface JobEndpointI {

	@WebMethod
	JobDTO createJob(@WebParam(name = "Bewerbung") JobDTO jobDTO)
			throws InvalidBewerbungException;

	@WebMethod
	List<JobDTO> holeAlleJobs();

	@WebMethod
	JobDTO jobAnsehen(@WebParam(name = "id") Long id);

	@WebMethod
	void deleteJob(@WebParam(name = "id") Long id);


}
