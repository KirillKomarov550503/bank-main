package com.netcracker.komarov.controllers.controller;


import com.netcracker.komarov.services.dto.entity.AdminDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("api/v1/admins")
public class AdminController {
    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody PersonDTO personDTO) {
        ResponseEntity responseEntity;
        AdminDTO dto = adminService.addAdmin(personDTO);
        if (dto == null) {
            responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            responseEntity = new ResponseEntity(dto, HttpStatus.CREATED);
        }
        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<AdminDTO> getAll() {
        return adminService.getAllAdmin();
    }
}
