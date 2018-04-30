package com.netcracker.komarov.services.dto;

import com.netcracker.komarov.services.dto.entity.AbstractDTO;

public interface Converter<DTO extends AbstractDTO, Entity> {
    DTO convertToDTO(Entity entity);

    Entity convertToEntity(DTO dto);
}

