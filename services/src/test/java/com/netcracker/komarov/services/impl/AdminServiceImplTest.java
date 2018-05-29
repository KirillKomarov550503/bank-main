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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private PasswordEncoder encoder;
    @Mock
    @Autowired
    private AdminService adminService;

    @Before
    public void init() {
        PersonDTO dto1 = new PersonDTO(0, "Max", "UL",
                1, 1, "Pessimist", "password");
        PersonDTO dto2 = new PersonDTO(0, "Pav", "Zar",
                2, 2, "Dancer", "disco");
        PersonDTO dto3 = new PersonDTO(0, "Kir", "Kom",
                3, 3, "Optimist", "qwerty");
        adminService.saveAdmin(dto1);
        adminService.saveAdmin(dto2);
        adminService.saveAdmin(dto3);
    }


    @Test
    public void addAdmin() {
        AdminDTO adminDTO = new AdminDTO(4, "Alex", "Khu", "WTF",
                encoder.encode("WTF"), 4, 4, Role.ADMIN);
        PersonDTO personDTO = new PersonDTO(0, "Alex", "Khu",
                4, 4, "WTF", "WTF");
        assertEquals(adminDTO, adminService.saveAdmin(personDTO));
    }

    @Test
    public void getAllAdmin() {
        AdminDTO dto1 = new AdminDTO(1, "Max", "UL",
                "Pessimist", encoder.encode("password"), 1, 1, Role.ADMIN);
        AdminDTO dto2 = new AdminDTO(2, "Pav", "Zar"
                , "Dancer", encoder.encode("disco"), 2, 2, Role.ADMIN);
        AdminDTO dto3 = new AdminDTO(3, "Kir", "Kom",
                "Optimist", encoder.encode("qwerty"), 3, 3, Role.ADMIN);
        Collection<AdminDTO> dtos = new ArrayList<>();
        dtos.add(dto1);
        dtos.add(dto2);
        dtos.add(dto3);
        assertEquals(dtos, adminService.findAllAdmins());
    }

    @Test
    public void update() {
        AdminDTO adminDTO = new AdminDTO(2, "Pavel", "Zaretskya",
                "Dancer", "disco", 101010, 224466, Role.ADMIN);
        AdminDTO res = new AdminDTO(2, "Pavel", "Zaretskya",
                "Dancer", encoder.encode("disco"), 101010, 224466, Role.ADMIN);
        assertEquals(res, adminService.update(adminDTO));
    }

    @Test
    public void deleteById() {
        adminService = mock(AdminService.class);
        doNothing().when(adminService).deleteById(isA(Long.class));
        adminService.deleteById(2);
        verify(adminService, times(1)).deleteById(2);
    }

    @Test
    public void findById() {
        AdminDTO dto = new AdminDTO(2, "Pav", "Zar"
                , "Dancer", encoder.encode("disco"), 2, 2, Role.ADMIN);
        assertEquals(dto, adminService.findById(2));
    }
}