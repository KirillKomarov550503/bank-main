package com.netcracker.komarov.services.dto;

public interface Converter<DTO, Entity> {
    DTO convertToDTO(Entity entity);

    Entity convertToEntity(DTO dto);
}

