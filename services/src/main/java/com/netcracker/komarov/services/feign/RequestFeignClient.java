package com.netcracker.komarov.services.feign;

import com.netcracker.komarov.services.dto.entity.RequestDTO;
import com.netcracker.komarov.services.exception.ExternalServiceException;
import com.netcracker.komarov.services.exception.FeignConfiguration;
import com.netcracker.komarov.services.exception.LogicException;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Collection;

@FeignClient(name = "bank-request", configuration = FeignConfiguration.class)
public interface RequestFeignClient {
    @PostMapping(value = "/bank/v1/requests")
    ResponseEntity<RequestDTO> save(@RequestBody RequestDTO requestDTO);

    @GetMapping(value = "/bank/v1/requests")
    ResponseEntity<Collection<RequestDTO>> findAllRequests();

    @DeleteMapping(value = "/bank/v1/requests/{requestId}")
    ResponseEntity<Void> deleteById(@PathVariable("requestId") long requestId);

    @GetMapping(value = "/bank/v1/requests/{requestId}")
    ResponseEntity<RequestDTO> findById(@PathVariable("requestId") long requestId);
}

