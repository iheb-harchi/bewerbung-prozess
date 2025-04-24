package org.mycompany.bewerbungssystem.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mycompany.bewerbungssystem.domain.bewerbung.BewerbungEntity;

import com.mycompany.bewerbung.BewerbungDTO;

import jakarta.enterprise.context.ApplicationScoped;

@Mapper(componentModel = "cdi")
@ApplicationScoped
public interface BewerbungMapper {


	BewerbungDTO toDTO(BewerbungEntity entity);

	@Mapping(target = "eingereichtAm", ignore = true)
	BewerbungEntity toEntity(BewerbungDTO dto);
}
