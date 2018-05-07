package com.netcracker.komarov.controllers.controller;


import com.google.gson.Gson;
import com.netcracker.komarov.services.dto.entity.AdminDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.AdminService;
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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody PersonDTO personDTO) {
        Gson gson = new Gson();
        AdminDTO dto = adminService.addAdmin(personDTO);
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

    @RequestMapping(value = "/admins/{adminId}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody AdminDTO adminDTO, @PathVariable long adminId) {
        Gson gson = new Gson();
        AdminDTO dto = adminService.update(adminDTO, adminId);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dto));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/admins/{adminId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long adminId) {
        adminService.deleteById(adminId);
        Gson gson = new Gson();
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson("Admin was deleted"));
    }
}
