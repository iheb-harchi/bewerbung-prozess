package org.mycompany.bewerbungssystem.infrastructure.persistence.repository;

import java.util.Collection;
import java.util.Optional;

import org.mycompany.bewerbungssystem.api.exception.ResourceNotFoundException;
import org.mycompany.bewerbungssystem.domain.job.JobEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class JobRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public JobEntity persistAndFlush(JobEntity entity) {
		if (entity.getId() == null) {
			entityManager.persist(entity);
		} else {
			entity = entityManager.merge(entity);
		}
		entityManager.flush(); // Sicherstellen, dass das Entity direkt in der DB gespeichert wird
		return entity;
	}

	public Optional<JobEntity> findById(Long id) {
		JobEntity entity = entityManager.find(JobEntity.class, id);
		return Optional.ofNullable(entity);
	}

	public Collection<JobEntity> findAll() {
		return entityManager.createQuery("SELECT j FROM JobEntity j", JobEntity.class).getResultList();

	}

	public void deleteById(Long id) {
		JobEntity entity = entityManager.find(JobEntity.class, id);
		if (entity != null) {
			entityManager.remove(entity);
		}else {
			throw new ResourceNotFoundException(" Jon not found id: " + id);
		}

	}

}
