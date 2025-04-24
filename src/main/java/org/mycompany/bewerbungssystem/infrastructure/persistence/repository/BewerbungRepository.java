package org.mycompany.bewerbungssystem.infrastructure.persistence.repository;


import static com.mycompany.jooq.generated.Tables.BEWERBUNG;

import java.util.List;
import java.util.Optional;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.mycompany.bewerbung.BewerbungFilter;
import org.mycompany.bewerbungssystem.domain.bewerbung.BewerbungEntity;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BewerbungRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	DSLContext dsl;

	@Transactional
	public BewerbungEntity save(BewerbungEntity bewerbungEntity) {
		if (bewerbungEntity.getId() == null) {
			entityManager.persist(bewerbungEntity);
		} else {
			bewerbungEntity = entityManager.merge(bewerbungEntity);
		}
		return bewerbungEntity;
	}

	public List<BewerbungEntity> findAll() {
		return entityManager.createQuery("SELECT b FROM BewerbungEntity b", BewerbungEntity.class).getResultList();
	}

	public Optional<BewerbungEntity> findById(Long id) {
		BewerbungEntity entity = entityManager.find(BewerbungEntity.class, id);
		return Optional.ofNullable(entity);
	}

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

	@Transactional
	public void delete(BewerbungEntity bewerbungEntity) {
		entityManager.remove(bewerbungEntity);
	}

	public List<BewerbungEntity> filterBewerbungen() {
		return findAll();
	}

	public List<BewerbungEntity> filterBewerbungen(BewerbungFilter filter) {
		return dsl
				.select(BEWERBUNG.ID, BEWERBUNG.BEWERBER_ID, BEWERBUNG.JOB_ID, BEWERBUNG.STATUS, BEWERBUNG.ANSCHREIBEN,
						BEWERBUNG.EINGEREICHT_AM)
				.from(BEWERBUNG).where(createConditions(filter)).fetchInto(BewerbungEntity.class);
	}

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
			condition = condition
					.and(BEWERBUNG.EINGEREICHT_AM.between(filter.getEingereichtVon().atStartOfDay(),
							filter.getEingereichtBis().atStartOfDay()));
		} else {
			if (filter.getEingereichtVon() != null) {
				condition = condition.and(BEWERBUNG.EINGEREICHT_AM.ge(filter.getEingereichtVon().atStartOfDay()));
			}
			if (filter.getEingereichtBis() != null) {
				condition = condition.and(BEWERBUNG.EINGEREICHT_AM.ne(filter.getEingereichtBis().atStartOfDay()));
			}
		}

		return condition;
	}

}
