package org.mycompany.bewerbungssystem.application.service;

import java.net.URL;

import org.mycompany.bewerbungssystem.api.exception.InvalidBewerbungException;
import org.mycompany.bewerbungssystem.api.exception.ResourceNotFoundException;
import org.mycompany.bewerbungssystem.application.mapper.BewerbungMapper;
import org.mycompany.bewerbungssystem.application.validation.AbstractXsdValidator;
import org.mycompany.bewerbungssystem.domain.bewerbung.BewerbungEntity;
import org.mycompany.bewerbungssystem.infrastructure.persistence.repository.BewerbungRepository;

import com.mycompany.bewerbung.BewerbungDTO;
import com.mycompany.bewerbung.BewerbungStatus;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BewerbungService {

	@Inject
	private BewerbungRepository bewerbungRepository;

	@Inject
	private BewerbungMapper bewerbungMapper;

	@Inject
	private BewerbungHistoryService bewerbungHistoryService;

	public BewerbungDTO bewerbungAnlegen(BewerbungDTO bewerbungDTO) throws InvalidBewerbungException {
		try {

			URL xsdUrl = getClass().getClassLoader().getResource("xsd/bewerbung.xsd");

			if (xsdUrl == null) {
				throw new InvalidBewerbungException("XSD-Datei nicht gefunden");
			}

			// Konvertiere die URL zu einem String (Pfad)
			String xsdPath = xsdUrl.getPath();
			// XSD Validierung der BewerbungDTO
			if (!AbstractXsdValidator.validateDto(bewerbungDTO, BewerbungDTO.class,
					xsdPath)) {
				throw new InvalidBewerbungException("Bewerbung entspricht nicht dem XSD-Schema");
			}
			BewerbungEntity bewerbungEntity = bewerbungMapper.toEntity(bewerbungDTO);

			bewerbungEntity = bewerbungRepository.save(bewerbungEntity);

			return bewerbungMapper.toDTO(bewerbungEntity);
		} catch (Exception e) {
			throw new RuntimeException("Fehler beim Anlegen der Bewerbung", e);
		}
	}

	public BewerbungDTO bewerbungEinsehen(Long id) {
		BewerbungEntity bewerbungEntity = bewerbungRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Bewerbung nicht gefunden"));
		return bewerbungMapper.toDTO(bewerbungEntity);
	}

	public void bewerbungLoeschen(Long id) {
		BewerbungEntity bewerbungEntity = bewerbungRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Bewerbung nicht gefunden"));
		bewerbungRepository.delete(bewerbungEntity);
	}

	public void statusAendern(Long id, BewerbungStatus neuerStatus) {
		BewerbungEntity bewerbungEntity = bewerbungRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Bewerbung nicht gefunden"));
		BewerbungStatus alterStatus = bewerbungEntity.getStatus();
		bewerbungEntity.setStatus(neuerStatus);
		bewerbungRepository.save(bewerbungEntity);
		bewerbungHistoryService.saveBewerbungHistory(bewerbungEntity, alterStatus, neuerStatus, false);

	}

}
