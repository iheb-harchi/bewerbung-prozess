package org.mycompany.bewerbungssystem.application.mapper;

import org.mapstruct.Mapper;
import org.mycompany.bewerbungssystem.domain.job.JobEntity;

import com.mycompany.job.JobDTO;

import jakarta.enterprise.context.ApplicationScoped;

@Mapper(componentModel = "cdi")
@ApplicationScoped
public interface JobMapper {
	JobDTO toDTO(JobEntity entity);

	JobEntity toEntity(JobDTO dto);

}
