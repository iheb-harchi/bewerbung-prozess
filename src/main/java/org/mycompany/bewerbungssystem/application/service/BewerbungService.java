package org.mycompany.bewerbungssystem.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.mycompany.bewerbung.BewerbungFilter;
import org.mycompany.bewerbungssystem.api.exception.InvalidBewerbungException;
import org.mycompany.bewerbungssystem.api.exception.ResourceNotFoundException;
import org.mycompany.bewerbungssystem.application.mapper.BewerbungMapper;
import org.mycompany.bewerbungssystem.domain.bewerbung.BewerbungEntity;
import org.mycompany.bewerbungssystem.infrastructure.persistence.repository.BewerbungRepository;
import org.mycompany.common.BewerbungStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycompany.bewerbung.BewerbungDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BewerbungService {

	private static final Logger LOG = LoggerFactory.getLogger(BewerbungService.class);

	@Inject
	BewerbungRepository bewerbungRepository;

	@Inject
	BewerbungHistoryService historyService;

	@Inject
	BewerbungMapper bewerbungMapper;

	@Inject
	BewerbungHistoryService bewerbungHistoryService;

	@Transactional
	public BewerbungDTO createBewerbung(BewerbungDTO bewerbungDTO) throws InvalidBewerbungException {
		BewerbungEntity entity = bewerbungMapper.toEntity(bewerbungDTO);
		entity = persistBewerbung(entity);

		LOG.info("Created new application with ID: {}", entity.getId());
		return bewerbungMapper.toDTO(entity);
	}

	private BewerbungEntity persistBewerbung(BewerbungEntity entity) {
		try {
			return bewerbungRepository.persistAndFlush(entity);
		} catch (Exception e) {
			LOG.error("Failed to persist application", e);
			throw new IllegalStateException("Failed to save application", e);
		}
	}

	@Transactional
	public BewerbungDTO getBewerbung(Long id) {
		return bewerbungRepository.findById(id).map(bewerbungMapper::toDTO).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Application with ID %d not found", id)));
	}

	public List<BewerbungDTO> getAllBewerbungen() {
		return bewerbungRepository.findAll().stream().map(bewerbungMapper::toDTO).collect(Collectors.toList());
	}

	@Transactional
	public void deleteBewerbung(Long id) {
		bewerbungRepository.deleteById(id);
		LOG.info("Deleted application with ID: {}", id);
	}

	@Transactional
	public void updateStatus(Long id, BewerbungStatus newStatus) {
		BewerbungEntity entity = bewerbungRepository.findById(id)
				.orElseThrow(
				() -> new ResourceNotFoundException(String.format("Bewerbung with ID %d not found", id)));

		BewerbungStatus oldStatus = entity.getStatus();
		if (oldStatus.equals(newStatus)) {
			LOG.info("Status hasn't been changed  for the status Bewerbung ID {}: {} -> {}", id, oldStatus, newStatus);
		} else {
			entity.setStatus(newStatus);

			bewerbungRepository.persistAndFlush(entity);
			historyService.saveBewerbungHistory(id, newStatus, oldStatus, false);
			LOG.info("Updated status for Bewerbung ID {}: {} -> {}", id, oldStatus, newStatus);
		}

	}

	public List<BewerbungDTO> filterBewerbungen(BewerbungFilter bewerbungFilter) {

		return bewerbungRepository.filterBewerbungen(bewerbungFilter).stream().map(bewerbungMapper::toDTO)
				.collect(Collectors.toList());
	}

}