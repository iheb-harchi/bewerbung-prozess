package org.mycompany.bewerbungssystem.infrastructure.persistence.repository;


import java.util.List;

import  org.mycompany.bewerbungssystem.domain.bewerbung.history.BewerbungHistoryEntity;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BewerbungHistoryRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void save(BewerbungHistoryEntity historyEntity) {
		entityManager.persist(historyEntity);
	}

	public List<BewerbungHistoryEntity> findByBewerbungId(Long bewerbungId) {
		TypedQuery<BewerbungHistoryEntity> query = entityManager.createQuery(
				"SELECT b FROM BewerbungHistoryEntity b WHERE b.bewerbungId = :bewerbungId ORDER BY b.id DESC",
				BewerbungHistoryEntity.class);
		query.setParameter("bewerbungId", bewerbungId);
		return query.getResultList();
	}

	public BewerbungHistoryEntity findLastByBewerbungId(Long bewerbungId) {
		TypedQuery<BewerbungHistoryEntity> query = entityManager.createQuery(
				"SELECT b FROM BewerbungHistoryEntity b WHERE b.bewerbungId = :bewerbungId ORDER BY b.id DESC",
				BewerbungHistoryEntity.class);
		query.setParameter("bewerbungId", bewerbungId);
		query.setMaxResults(1);
		return query.getResultList().isEmpty() ? null : query.getSingleResult();
	}
}
