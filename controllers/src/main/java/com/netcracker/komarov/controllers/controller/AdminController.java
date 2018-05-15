package com.netcracker.komarov.controllers.controller;


import com.netcracker.komarov.services.dto.entity.AdminDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.AdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/bank/v1/admins")
public class AdminController {
    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @ApiOperation(value = "Creation of new admin")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody PersonDTO requestPersonDTO) {
        AdminDTO dto = adminService.addAdmin(requestPersonDTO);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = internalServerError("Server error");
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(dto);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all admins")
    @RequestMapping(value = "/admins", method = RequestMethod.GET)
    public ResponseEntity getAllAdmins() {
        ResponseEntity responseEntity;
        Collection<AdminDTO> dtos = adminService.getAllAdmin();
        if (dtos == null) {
            responseEntity = internalServerError("Server error");
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(dtos);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Updating information about admin")
    @RequestMapping(value = "/admins/{adminId}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody AdminDTO requestAdminDTO, @PathVariable long adminId) {
        ResponseEntity responseEntity;
        try {
            requestAdminDTO.setId(adminId);
            AdminDTO dto = adminService.update(requestAdminDTO);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (NotFoundException e) {
            responseEntity = notFound(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Deleting admin from system by ID")
    @RequestMapping(value = "/admins/{adminId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long adminId) {
        ResponseEntity responseEntity;
        try {
            adminService.deleteById(adminId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).build();
        } catch (NotFoundException e) {
            responseEntity = notFound(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting admin by ID")
    @RequestMapping(value = "/admins/{adminId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long adminId) {
        ResponseEntity responseEntity;
        try {
            AdminDTO dto = adminService.findById(adminId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (NotFoundException e) {
            responseEntity = notFound(e.getMessage());
        }
        return responseEntity;
    }

    private ResponseEntity notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    private ResponseEntity internalServerError(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
}
