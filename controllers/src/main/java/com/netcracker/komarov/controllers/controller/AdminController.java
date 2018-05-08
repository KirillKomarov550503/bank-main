package com.netcracker.komarov.controllers.controller;


import com.google.gson.Gson;
import com.netcracker.komarov.services.dto.entity.AdminDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.AdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("bank/v1/admins")
public class AdminController {
    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @ApiOperation(value = "Creation of new admin")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody PersonDTO requestPersonDTO) {
        Gson gson = new Gson();
        AdminDTO dto = adminService.addAdmin(requestPersonDTO);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED)
                    .body(gson.toJson(dto));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all admins")
    @RequestMapping(value = "/admins", method = RequestMethod.GET)
    public ResponseEntity getAllAdmins() {
        ResponseEntity responseEntity;
        Gson gson = new Gson();
        Collection<AdminDTO> dtos = adminService.getAllAdmin();
        if (dtos == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dtos.isEmpty() ? "Empty list of admins" : dtos));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Updating information about admin")
    @RequestMapping(value = "/admins/{adminId}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody AdminDTO requestAdminDTO, @PathVariable long adminId) {
        Gson gson = new Gson();
        AdminDTO adminDTO = adminService.findById(adminId);
        ResponseEntity responseEntity;
        if (adminDTO == null) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(gson.toJson("No such admin"));
        } else {
            requestAdminDTO.setId(adminId);
            AdminDTO dto = adminService.update(requestAdminDTO);
            if (dto == null) {
                responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(gson.toJson("Server error"));
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.OK)
                        .body(gson.toJson(dto));
            }
        }
        return responseEntity;
    }

    @ApiOperation(value = "Deleting admin from system by ID")
    @RequestMapping(value = "/admins/{adminId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long adminId) {
        Gson gson = new Gson();
        ResponseEntity responseEntity;
        AdminDTO adminDTO = adminService.findById(adminId);
        if (adminDTO == null) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(gson.toJson("No such admin"));
        } else {
            adminService.deleteById(adminId);
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson("Admin was deleted"));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting admin by ID")
    @RequestMapping(value = "/admins/{adminId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long adminId) {
        Gson gson = new Gson();
        ResponseEntity responseEntity;
        AdminDTO dto = adminService.findById(adminId);
        if (dto == null) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(gson.toJson("No such admin in database"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dto));
        }
        return responseEntity;
    }
}
