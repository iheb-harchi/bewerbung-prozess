package org.mycompany.bewerbungssystem.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.generated.tables.Bewerbung;
import org.jooq.impl.DSL;
import org.mycompany.bewerbung.BewerbungFilter;
import org.mycompany.bewerbungssystem.domain.bewerbung.BewerbungEntity;
import org.mycompany.common.BewerbungStatus;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BewerbungRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private final Bewerbung BEWERBUNG = Bewerbung.BEWERBUNG;

	@Inject
	DSLContext dsl;

	/**
	 * Persistiert und flusht das BewerbungEntity in der Datenbank.
	 * 
	 * @param bewerbungEntity Das zu persistierende BewerbungEntity.
	 * @return Das persistierte BewerbungEntity.
	 */
	@Transactional
	public BewerbungEntity persistAndFlush(BewerbungEntity bewerbungEntity) {
		if (bewerbungEntity.getId() == null) {
			bewerbungEntity.setEingereichtAm(LocalDateTime.now());
			bewerbungEntity.setStatus(BewerbungStatus.EINGEREICHT);
			entityManager.persist(bewerbungEntity);
		} else {
			bewerbungEntity = entityManager.merge(bewerbungEntity);
		}
		entityManager.flush(); // Sicherstellen, dass das Entity direkt in der DB gespeichert wird
		return bewerbungEntity;
	}

	/**
	 * Findet eine Bewerbung anhand ihrer ID.
	 * 
	 * @param id Die ID der Bewerbung.
	 * @return Optional mit BewerbungEntity oder leer, wenn nicht gefunden.
	 */
	public Optional<BewerbungEntity> findById(Long id) {
		BewerbungEntity entity = entityManager.find(BewerbungEntity.class, id);
		return Optional.ofNullable(entity);
	}

	/**
	 * Löscht eine Bewerbung anhand ihrer ID.
	 * 
	 * @param id Die ID der Bewerbung, die gelöscht werden soll.
	 */
	@Transactional
	public void deleteById(Long id) {
		BewerbungEntity entity = entityManager.find(BewerbungEntity.class, id);
		if (entity != null) {
			entityManager.remove(entity);
		}
	}

	/**
	 * Findet alle Bewerbungen in der Datenbank.
	 * 
	 * @return Eine Liste aller BewerbungEntities.
	 */
	public List<BewerbungEntity> findAll() {
		return entityManager.createQuery("SELECT b FROM BewerbungEntity b", BewerbungEntity.class).getResultList();
	}

	/**
	 * Findet Bewerbungen anhand eines Bewerber-IDs.
	 * 
	 * @param bewerberId Die ID des Bewerbers.
	 * @return Optional mit BewerbungEntity oder leer, wenn nicht gefunden.
	 */
	public Optional<BewerbungEntity> findByBewerberId(long bewerberId) {
		try {
			BewerbungEntity entity = entityManager
					.createQuery("SELECT b FROM BewerbungEntity b WHERE b.bewerberId = :bewerberId",
							BewerbungEntity.class)
					.setParameter("bewerberId", bewerberId).getSingleResult();
			return Optional.of(entity);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	/**
	 * Findet Bewerbungen anhand einer Job-ID.
	 * 
	 * @param jobId Die ID des Jobs.
	 * @return Optional mit BewerbungEntity oder leer, wenn nicht gefunden.
	 */
	public Optional<BewerbungEntity> findByJobId(long jobId) {
		try {
			BewerbungEntity entity = entityManager
					.createQuery("SELECT b FROM BewerbungEntity b WHERE b.jobId = :jobId", BewerbungEntity.class)
					.setParameter("jobId", jobId).getSingleResult();
			return Optional.of(entity);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	/**
	 * Filtert Bewerbungen basierend auf einem Filter.
	 * 
	 * @param filter Der Filter für die Bewerbungen.
	 * @return Eine Liste von BewerbungEntities, die dem Filter entsprechen.
	 */
	public List<BewerbungEntity> filterBewerbungen(BewerbungFilter filter) {
		return dsl
				.select(BEWERBUNG.ID, BEWERBUNG.BEWERBER_ID, BEWERBUNG.JOB_ID, BEWERBUNG.STATUS, BEWERBUNG.ANSCHREIBEN,
						BEWERBUNG.EINGEREICHT_AM)
				.from(BEWERBUNG).where(createConditions(filter)).fetchInto(BewerbungEntity.class);
	}

	/**
	 * Erzeugt die Bedingungen basierend auf dem Bewerbungsfilter.
	 * 
	 * @param filter Der Filter mit verschiedenen Parametern.
	 * @return Die erstellte Condition.
	 */
	private Condition createConditions(BewerbungFilter filter) {
		Condition condition = DSL.noCondition();

		if (filter.getBewerbungId() != null) {
			condition = condition.and(BEWERBUNG.ID.eq(filter.getBewerbungId()));
		}
		if (filter.getJobId() != null) {
			condition = condition.and(BEWERBUNG.JOB_ID.eq(filter.getJobId()));
		}
		if (filter.getStatus() != null) {
			condition = condition.and(BEWERBUNG.STATUS.eq(filter.getStatus()));
		}
		if (filter.getAnschreiben() != null) {
			condition = condition.and(BEWERBUNG.ANSCHREIBEN.likeIgnoreCase("%" + filter.getAnschreiben() + "%"));
		}
		if (filter.getEingereichtVon() != null && filter.getEingereichtBis() != null) {
			condition = condition.and(BEWERBUNG.EINGEREICHT_AM.between(filter.getEingereichtVon().atStartOfDay(),
					filter.getEingereichtBis().atStartOfDay()));
		} else {
			if (filter.getEingereichtVon() != null) {
				condition = condition.and(BEWERBUNG.EINGEREICHT_AM.ge(filter.getEingereichtVon().atStartOfDay()));
			}
			if (filter.getEingereichtBis() != null) {
				condition = condition.and(BEWERBUNG.EINGEREICHT_AM.le(filter.getEingereichtBis().atStartOfDay()));
			}
		}

		return condition;
	}
}
