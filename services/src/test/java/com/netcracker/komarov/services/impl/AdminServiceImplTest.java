package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Role;
import com.netcracker.komarov.services.ServiceContext;
import com.netcracker.komarov.services.dto.entity.AdminDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.AdminService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@Transactional
@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceContext.class)
public class AdminServiceImplTest {

    @Mock
    @Autowired
    private AdminService adminService;

    @Before
    public void init() {
        PersonDTO dto1 = new PersonDTO(0, "Max", "UL", 1, 1);
        PersonDTO dto2 = new PersonDTO(0, "Pav", "Zar", 2, 2);
        PersonDTO dto3 = new PersonDTO(0, "Kir", "Kom", 3, 3);
        adminService.addAdmin(dto1);
        adminService.addAdmin(dto2);
        adminService.addAdmin(dto3);
    }


    @Test
    public void addAdmin() {
        AdminDTO adminDTO = new AdminDTO(4, "Alex", "Khu", null,
                null, 4, 4, Role.ADMIN);
        PersonDTO personDTO = new PersonDTO(0, "Alex", "Khu", 4, 4);
        assertEquals(adminDTO, adminService.addAdmin(personDTO));
    }

    @Test
    public void getAllAdmin() {
        AdminDTO dto1 = new AdminDTO(1, "Max", "UL",
                null, null, 1, 1, Role.ADMIN);
        AdminDTO dto2 = new AdminDTO(2, "Pav", "Zar"
                , null, null, 2, 2, Role.ADMIN);
        AdminDTO dto3 = new AdminDTO(3, "Kir", "Kom",
                null, null, 3, 3, Role.ADMIN);
        Collection<AdminDTO> dtos = new ArrayList<>();
        dtos.add(dto1);
        dtos.add(dto2);
        dtos.add(dto3);
        assertEquals(dtos, adminService.getAllAdmin());
    }

    @Test
    public void update() {
        AdminDTO adminDTO = new AdminDTO(2, "Pavel", "Zaretskya",
                null, null, 101010, 224466, Role.ADMIN);
        assertEquals(adminDTO, adminService.update(adminDTO));
    }

    @Test
    public void deleteById() {
        adminService = mock(AdminService.class);
        doNothing().when(adminService).deleteById(isA(Long.class));
        adminService.deleteById(2);
        verify(adminService, times(1)).deleteById(2);
    }
}