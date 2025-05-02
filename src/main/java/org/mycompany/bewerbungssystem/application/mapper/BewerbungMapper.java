package org.mycompany.bewerbungssystem.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mycompany.bewerbungssystem.domain.bewerber.BewerberEntity;
import org.mycompany.bewerbungssystem.domain.bewerbung.BewerbungEntity;
import org.mycompany.bewerbungssystem.domain.job.JobEntity;

import com.mycompany.bewerbung.BewerbungDTO;

import jakarta.enterprise.context.ApplicationScoped;

@Mapper(componentModel = "cdi")
@ApplicationScoped
public interface BewerbungMapper {

	@Mapping(target = "bewerberId", source = "bewerber.id")
	@Mapping(target = "jobId", source = "job.id")
	BewerbungDTO toDTO(BewerbungEntity entity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "eingereichtAm", ignore = true)
	@Mapping(target = "bewerber", source = "bewerberId")
	@Mapping(target = "job", source = "jobId")
	BewerbungEntity toEntity(BewerbungDTO dto);

	default BewerberEntity map(Long id) {
		if (id == null)
			return null;
		BewerberEntity b = new BewerberEntity();
		b.setId(id);
		return b;
	}

	default JobEntity mapJob(Long id) {
		if (id == null)
			return null;
		JobEntity job = new JobEntity();
		job.setId(id);
		return job;
	}

}
