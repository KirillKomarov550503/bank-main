package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.entity.AdminDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;

import java.util.Collection;

public interface AdminService {
    AdminDTO addAdmin(PersonDTO personDTO);

    Collection<AdminDTO> getAllAdmin();

    AdminDTO update(AdminDTO adminDTO);

    void deleteById(long adminId);

    AdminDTO findById(long adminId);
}
