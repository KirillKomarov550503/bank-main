package com.netcracker.komarov.services.feign;

import com.netcracker.komarov.services.dto.entity.RequestDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@FeignClient(name = "bank-request")
public interface RequestFeignClient {
    @PostMapping(value = "/bank/v1/requests")
    ResponseEntity<RequestDTO> save(@RequestBody RequestDTO requestDTO);

    @GetMapping(value = "/bank/v1/requests")
    ResponseEntity<Collection<RequestDTO>> findAllRequests();

    @DeleteMapping(value = "/bank/v1/requests/{requestId}")
    ResponseEntity<Void> deleteById(@PathVariable("requestId") long requestId,
                                    @RequestParam(name = "status", required = false) String status);

    @GetMapping(value = "/bank/v1/requests/{requestId}")
    ResponseEntity<RequestDTO> findById(@PathVariable("requestId") long requestId);
}

