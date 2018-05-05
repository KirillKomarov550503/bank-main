package com.netcracker.komarov.controllers.controller;


import com.netcracker.komarov.controllers.exception.ServerException;
import com.netcracker.komarov.services.dto.entity.AdminDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admins")
public class AdminController {
    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public AdminDTO add(@RequestBody PersonDTO personDTO) {
        AdminDTO dto = adminService.addAdmin(personDTO);
        if (dto == null) {
            throw new ServerException("Server can't create new admin");
        }
        return dto;
    }
}
