package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.services.ServiceContext;
import com.netcracker.komarov.services.dto.RequestStatus;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.dto.entity.CardDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.dto.entity.RequestDTO;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.CardService;
import com.netcracker.komarov.services.interfaces.ClientService;
import com.netcracker.komarov.services.interfaces.RequestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@Transactional
@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceContext.class)
public class RequestServiceImplTest {

    @Mock
    @Autowired
    private RequestService requestService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @Before
    public void init() {
        clientService.save(new PersonDTO(0, "Kirill", "Komarov", 1, 1));
        accountService.createAccount(new AccountDTO(false, 0.0), 1);
        accountService.createAccount(new AccountDTO(false, 0.0), 1);
        cardService.createCard(new CardDTO(0, false, 0, 1, 1111));
        cardService.createCard(new CardDTO(0, false, 0, 1, 2222));
        cardService.createCard(new CardDTO(0, false, 0, 2, 3333));

        accountService.lockAccount(2);
        requestService.saveRequest(2, RequestStatus.ACCOUNT);

        cardService.lockCard(2);
        requestService.saveRequest(2, RequestStatus.CARD);

        cardService.lockCard(1);
        requestService.saveRequest(1, RequestStatus.CARD);
    }

    @Test
    public void saveRequest() {
        accountService.lockAccount(1);
        RequestDTO requestDTO = new RequestDTO(4, new AccountDTO(1, true, 0), null);
        assertEquals(requestDTO, requestService.saveRequest(1, RequestStatus.ACCOUNT));
    }

    @Test
    public void findAllRequests() {
        RequestDTO dto1 = new RequestDTO(1, new AccountDTO(2, true, 0), null);
        RequestDTO dto2 = new RequestDTO(2, null,
                new CardDTO(2, true, 0, 1, 2222));
        RequestDTO dto3 = new RequestDTO(3, null,
                new CardDTO(1, true, 0, 1, 1111));
        Collection<RequestDTO> dtos = Stream.of(dto1, dto2, dto3).collect(Collectors.toList());
        assertEquals(dtos, requestService.findAllRequests());
    }

    @Test
    public void findById() {
        RequestDTO dto = new RequestDTO(2, null,
                new CardDTO(2, true, 0, 1, 2222));
        assertEquals(dto, requestService.findById(2));
    }

    @Test
    public void delete() {
        requestService = mock(RequestService.class);
        doNothing().when(requestService).delete(isA(Long.class));
        requestService.delete(8432582);
        verify(requestService, times(1)).delete(8432582);
    }
}