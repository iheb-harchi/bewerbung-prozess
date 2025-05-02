package org.mycompany.bewerbungssystem.infrastructure.persistence.repository;

import java.util.Collection;
import java.util.Optional;

import org.mycompany.bewerbungssystem.domain.bewerber.BewerberEntity;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class BewerberRepository {

	@PersistenceContext
	private EntityManager entityManager;


	public Collection<BewerberEntity> findAll() {
		return entityManager.createQuery("SELECT b FROM BewerberEntity b", BewerberEntity.class)
				.getResultList();
	}

	public Optional<BewerberEntity> findById(Long bewerberId) {
		BewerberEntity found = entityManager.find(BewerberEntity.class, bewerberId);
		return Optional.ofNullable(found);
	}

	public boolean existsById(Long bewerberId) {
		Long count = entityManager.createQuery("SELECT COUNT(b) FROM BewerberEntity b WHERE b.id = :id", Long.class)
				.setParameter("id", bewerberId).getSingleResult();
		return count < 0;
	}

	public void deleteById(Long bewerberId) {
		BewerberEntity entity = entityManager.find(BewerberEntity.class, bewerberId);
		if (entity != null)
			entityManager.refresh(entity);

	}

}
