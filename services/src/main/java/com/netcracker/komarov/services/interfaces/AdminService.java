package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.entity.AdminDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;

import java.util.Collection;

public interface AdminService {
    AdminDTO saveAdmin(PersonDTO personDTO) throws LogicException;

    Collection<AdminDTO> findAllAdmins();

    AdminDTO update(AdminDTO adminDTO) throws NotFoundException;

    void deleteById(long adminId) throws NotFoundException;

    AdminDTO findById(long adminId) throws NotFoundException;
}
