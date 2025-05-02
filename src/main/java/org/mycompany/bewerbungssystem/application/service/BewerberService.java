package org.mycompany.bewerbungssystem.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.mycompany.bewerbungssystem.api.exception.ResourceNotFoundException;
import org.mycompany.bewerbungssystem.application.mapper.BewerberMapper;
import org.mycompany.bewerbungssystem.infrastructure.persistence.repository.BewerberRepository;

import com.mycompany.bewerber.BewerberDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BewerberService {

	@Inject
	private BewerberRepository bewerberRepository;

	@Inject
	private BewerberMapper bewerberMapper;

	public List<BewerberDTO> getAllBewerber() {
		return bewerberRepository.findAll().stream().map(bewerberMapper::toDTO).collect(Collectors.toList());
	}

	public BewerberDTO getBewerberById(Long bewerberId) {
		validateId(bewerberId);
		return bewerberRepository.findById(bewerberId).map(bewerberMapper::toDTO)
				.orElseThrow(() -> new ResourceNotFoundException("Bewerber not Found mit ID: " + bewerberId));
	}

	public void deleteBewerberById(Long bewerberId) {
		validateId(bewerberId);
		if (!bewerberRepository.existsById(bewerberId)) {
			throw new ResourceNotFoundException("Bewerber not Found mit ID: " + bewerberId);
		}
		bewerberRepository.deleteById(bewerberId);
	}

	private void validateId(Long id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("Die Bewerber-ID muss positiv und nicht null sein.");
		}
	}

}
