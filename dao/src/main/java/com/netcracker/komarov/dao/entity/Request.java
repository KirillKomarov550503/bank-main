package com.netcracker.komarov.dao.entity;

import java.util.Objects;

public class Request extends BaseEntity {
    private long requestId;
    private RequestStatus requestStatus;

    public Request() {
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Request request = (Request) o;
        return requestId == request.requestId &&
                requestStatus == request.requestStatus;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), requestId, requestStatus);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", requestId=" + requestId +
                ", requestStatus=" + requestStatus +
                '}';
    }
}
