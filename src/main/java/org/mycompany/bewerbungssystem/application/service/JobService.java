package org.mycompany.bewerbungssystem.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.mycompany.bewerbungssystem.api.exception.ResourceNotFoundException;
import org.mycompany.bewerbungssystem.application.mapper.JobMapper;
import org.mycompany.bewerbungssystem.domain.job.JobEntity;
import org.mycompany.bewerbungssystem.infrastructure.persistence.repository.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycompany.job.JobDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class JobService {

	private static final Logger LOG = LoggerFactory.getLogger(JobService.class);

	@Inject
	JobMapper mapper;
	@Inject
	JobRepository jobRepository;

	@Transactional
	public JobDTO createJob(JobDTO jobDTO) {
		try {
			JobEntity jobEntity = mapper.toEntity(jobDTO);
			jobRepository.persistAndFlush(jobEntity);
			LOG.info("Created new application with ID: {}", jobEntity.getId());
			return mapper.toDTO(jobEntity);
		} catch (Exception e) {
			LOG.error("Failed to persist job", e);
			throw new IllegalStateException("Failed to save job", e);
		}

	}

	@Transactional
	public JobDTO getJob(Long id) {
		return jobRepository.findById(id).map(mapper::toDTO).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Application with ID %d not found", id)));
	}

	public List<JobDTO> getAllJobs() {
		return jobRepository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	@Transactional
	public void deleteJob(Long id) {
		jobRepository.deleteById(id);
		LOG.info("Deleted Job with ID: {}", id);
	}

}
