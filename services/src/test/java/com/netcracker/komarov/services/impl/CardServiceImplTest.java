package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.services.ServiceContext;
import com.netcracker.komarov.services.dto.Status;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.dto.entity.CardDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.dto.entity.RequestDTO;
import com.netcracker.komarov.services.feign.RequestFeignClient;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.CardService;
import com.netcracker.komarov.services.interfaces.ClientService;
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
public class CardServiceImplTest {

    @Mock
    @Autowired
    private CardService cardService;

    @Autowired
    private RequestFeignClient requestFeignClient;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Before
    public void init() {
        clientService.save(new PersonDTO(0, "Kirill", "Komarov",
                1, 1, "Optimist", "qwerty"));
        accountService.saveAccount(new AccountDTO(false, 0.0), 1);
        accountService.saveAccount(new AccountDTO(false, 0.0), 1);
        cardService.save(new CardDTO(0, false, 0, 1, 1111));
        cardService.save(new CardDTO(0, false, 0, 1, 2222));
        cardService.save(new CardDTO(0, false, 0, 2, 3333));
    }

    @Test
    public void lockCard() {
        CardDTO cardDTO = new CardDTO(2, true, 0, 1, 2222);
        assertEquals(cardDTO, cardService.lockCard(2));
    }

    @Test
    public void createCard() {
        CardDTO dto = new CardDTO(4, false, 0, 2, 4444);
        assertEquals(dto, cardService.save(new CardDTO(0, false, 0, 2, 4444)));
    }

    @Test
    public void getCardsByClientIdAndLock() {
        Collection<CardDTO> dtos = new ArrayList<>();
        assertEquals("Must return empty list", dtos, cardService.findCardsByClientIdAndLock(1, true));
        cardService.lockCard(1);
        cardService.lockCard(2);
        dtos.add(new CardDTO(1, true, 0, 1, 1111));
        dtos.add(new CardDTO(2, true, 0, 1, 2222));
        assertEquals(dtos, cardService.findCardsByClientIdAndLock(1, true));
    }

    @Test
    public void getAllCardsByAccountId() {
        Collection<CardDTO> dtos = new ArrayList<>();
        dtos.add(new CardDTO(3, false, 0, 2, 3333));
        assertEquals(dtos, cardService.findCardsByAccountId(2));
    }

    @Test
    public void unlockCard() {
        cardService.lockCard(1);
        requestFeignClient.save(new RequestDTO(1L, Status.CARD));
        CardDTO dto = new CardDTO(1, false, 0, 1, 1111);
        assertEquals(dto, cardService.unlockCard(1));
    }

    @Test
    public void getAllCards() {
        Collection<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO(1, false, 0, 1, 1111));
        cards.add(new CardDTO(2, false, 0, 1, 2222));
        cards.add(new CardDTO(3, false, 0, 2, 3333));
        assertEquals(cards, cardService.findAllCards());
    }

    @Test
    public void deleteById() {
        cardService = mock(CardService.class);
        doNothing().when(cardService).deleteById(isA(Long.class));
        cardService.deleteById(3);
        verify(cardService, times(1)).deleteById(3);
    }

    @Test
    public void findById() {
        CardDTO cardDTO = new CardDTO(1, false, 0, 1, 1111);
        assertEquals(cardDTO, cardService.findById(1));
    }
}