package org.mycompany.bewerbungssystem.infrastructure.soap.job;

import java.util.List;

import org.mycompany.bewerbungssystem.application.service.JobService;

import com.mycompany.job.JobDTO;

import jakarta.inject.Inject;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService(serviceName = "JobService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class JobEndpointImpl implements JobEndpointI {

	@Inject
	private JobService service;

	@Override
	public JobDTO createJob(JobDTO jobDTO) {
		return service.createJob(jobDTO);
	}

	@Override
	public JobDTO jobAnsehen(Long id) {
		return service.getJob(id);
	}

	@Override
	public void deleteJob(Long id) {
		service.deleteJob(id);
	}

	@Override
	public List<JobDTO> holeAlleJobs() {
		return service.getAllJobs();
	}

}