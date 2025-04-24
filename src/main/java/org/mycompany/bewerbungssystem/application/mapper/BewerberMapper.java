package org.mycompany.bewerbungssystem.application.mapper;

import org.mapstruct.Mapper;
import org.mycompany.bewerbungssystem.domain.bewerber.BewerberEntity;

import com.mycompany.bewerber.BewerberDTO;

import jakarta.enterprise.context.ApplicationScoped;

@Mapper(componentModel = "cdi")
@ApplicationScoped
public interface BewerberMapper {

	BewerberDTO toDTO(BewerberEntity entity);

	BewerberEntity toEntity(BewerberDTO dto);
}
