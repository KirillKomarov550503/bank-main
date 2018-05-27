package com.netcracker.komarov.services.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.netcracker.komarov.services.dto.Status;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestDTO implements Serializable {
    private Long id;
    private Long entityId;
    private Status status;

    public RequestDTO() {
    }

    public RequestDTO(Long entityId, Status status) {
        this.entityId = entityId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestDTO that = (RequestDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(entityId, that.entityId) &&
                status == that.status;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, entityId, status);
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "id=" + id +
                ", entityId=" + entityId +
                ", status=" + status +
                '}';
    }
}

