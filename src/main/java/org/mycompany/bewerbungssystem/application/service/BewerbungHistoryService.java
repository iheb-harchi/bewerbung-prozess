package org.mycompany.bewerbungssystem.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.mycompany.bewerbungssystem.BewerbungHistoryDTO;
import org.mycompany.bewerbungssystem.application.mapper.BewerbungHistoryMapper;
import org.mycompany.bewerbungssystem.domain.bewerbung.history.BewerbungHistoryEntity;
import org.mycompany.bewerbungssystem.infrastructure.persistence.repository.BewerbungHistoryRepository;
import org.mycompany.common.BewerbungStatus;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BewerbungHistoryService {

    @Inject
    private BewerbungHistoryRepository bewerbungHistoryRepository;
	@Inject
	private BewerbungHistoryMapper bewerbungHistoryMapper;

	public boolean isStatusGeändert(Long bewerbungId, BewerbungStatus currentStatus) {

		BewerbungHistoryEntity lastHistoryEntry = bewerbungHistoryRepository.findLastByBewerbungId(bewerbungId);

		if (lastHistoryEntry != null) {
			if (lastHistoryEntry.isBewerberBenachrichtigt()) {
				return false;
			}

			return !lastHistoryEntry.getNeuerStatus().equals(currentStatus);
		}

		return true;
	}


	public void saveBewerbungHistory(Long bewerbungId, BewerbungStatus alterStatus,
			BewerbungStatus neuerStatus,
			boolean benachrichtigt) {
		BewerbungHistoryEntity historyEntry = new BewerbungHistoryEntity();
		historyEntry.setBewerbungId(bewerbungId);
		historyEntry.setAlterStatus(alterStatus);
		historyEntry.setNeuerStatus(neuerStatus);
		historyEntry.setBewerberBenachrichtigt(benachrichtigt);
		historyEntry.setGeaendertAm(LocalDateTime.now());
		historyEntry.setGeaendertVon("SYSTEM");

		bewerbungHistoryRepository.save(historyEntry);
	}

	public List<BewerbungHistoryDTO> getHistoryByBewerbungId(Long bewerbungId) {
		return bewerbungHistoryRepository.findByBewerbungId(bewerbungId).stream().map(bewerbungHistoryMapper::toDTO)
				.collect(Collectors.toList());

	}
}
