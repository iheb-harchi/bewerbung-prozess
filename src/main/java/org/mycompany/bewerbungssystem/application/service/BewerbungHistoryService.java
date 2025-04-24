package org.mycompany.bewerbungssystem.application.service;

import java.time.LocalDateTime;

import org.mycompany.bewerbungssystem.domain.bewerbung.BewerbungEntity;
import org.mycompany.bewerbungssystem.domain.bewerbung.history.BewerbungHistoryEntity;
import org.mycompany.bewerbungssystem.infrastructure.persistence.repository.BewerbungHistoryRepository;

import com.mycompany.bewerbung.BewerbungStatus;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BewerbungHistoryService {

    @Inject
    private BewerbungHistoryRepository bewerbungHistoryRepository;


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

	public void saveBewerbungHistory(BewerbungEntity bewerbung, BewerbungStatus alterStatus,
			BewerbungStatus neuerStatus,
			boolean benachrichtigt) {
		BewerbungHistoryEntity historyEntry = new BewerbungHistoryEntity();
		historyEntry.setBewerbungId(bewerbung.getId());
		historyEntry.setAlterStatus(alterStatus);
		historyEntry.setNeuerStatus(neuerStatus);
		historyEntry.setBewerberBenachrichtigt(benachrichtigt);
		historyEntry.setGeaendertAm(LocalDateTime.now());
		historyEntry.setGeaendertVon("SYSTEM");

		bewerbungHistoryRepository.save(historyEntry);
	}
}
