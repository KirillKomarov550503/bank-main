package com.netcracker.komarov.services.dto;

import java.io.Serializable;

public interface Converter<DTO, Entity> {
    DTO convertToDTO(Entity entity);

    Entity convertToEntity(DTO dto);
}

