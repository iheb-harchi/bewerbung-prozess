package org.mycompany.bewerbungssystem.application.mapper;

import org.mapstruct.Mapper;
import org.mycompany.bewerbungssystem.BewerbungHistoryDTO;
import org.mycompany.bewerbungssystem.domain.bewerbung.history.BewerbungHistoryEntity;

import jakarta.enterprise.context.ApplicationScoped;

@Mapper(componentModel = "cdi")
@ApplicationScoped
public interface BewerbungHistoryMapper {

	BewerbungHistoryDTO toDTO(BewerbungHistoryEntity entity);

	BewerbungHistoryEntity toEntity(BewerbungHistoryDTO dto);

}
