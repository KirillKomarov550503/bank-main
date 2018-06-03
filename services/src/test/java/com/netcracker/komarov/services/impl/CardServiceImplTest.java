package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.TestConfig;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.dto.entity.CardDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.feign.RequestFeignClient;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.CardService;
import com.netcracker.komarov.services.interfaces.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CardServiceImplTest {
    @Mock
    @Autowired
    private CardService cardService;

    @Autowired
    private PersonService personService;

    @Autowired
    private RequestFeignClient requestFeignClient;

    @Autowired
    private AccountService accountService;

    @Before
    public void init() {
        personService.save(new PersonDTO(0L, "Kirill", "Komarov",
                "1", "1", "Optimist", "qwerty"));
        accountService.saveAccount(new AccountDTO(false, 0.0), 1);
        accountService.saveAccount(new AccountDTO(false, 0.0), 1);
        cardService.save(new CardDTO(0L, false, 0.0, 1L, "1111"));
        cardService.save(new CardDTO(0L, false, 0.0, 1L, "2222"));
        cardService.save(new CardDTO(0L, false, 0.0, 2L, "3333"));
    }

    @Test
    public void lockCard() {
        CardDTO cardDTO = new CardDTO(2L, true, 0.0, 1L, null);
        assertEquals(cardDTO, cardService.lockCard(2));
    }

    @Test
    public void save() {
        CardDTO dto = new CardDTO(4L, false, 0.0, 2L, null);
        assertEquals(dto, cardService.save(new CardDTO(0L, false, 0.0, 2L, "4444")));
    }

    @Test
    public void findCardsByClientIdAndLock() {
        Collection<CardDTO> dtos = new ArrayList<>();
        assertEquals("Must return empty list", dtos, cardService.findCardsByClientIdAndLock(1, true));
        cardService.lockCard(1);
        cardService.lockCard(2);
        dtos.add(new CardDTO(1L, true, 0.0, 1L, null));
        dtos.add(new CardDTO(2L, true, 0.0, 1L, null));
        assertEquals(dtos, cardService.findCardsByClientIdAndLock(1, true));
    }

    @Test
    public void findCardsByAccountId() {
        Collection<CardDTO> dtos = new ArrayList<>();
        dtos.add(new CardDTO(3L, false, 0.0, 2L, null));
        assertEquals(dtos, cardService.findCardsByAccountId(2));
    }

    @Test
    public void unlockCard() {
        cardService = mock(CardService.class);
        CardDTO dto = new CardDTO(1L, false, 0.0, 1L, null);
        doReturn(dto).when(cardService).unlockCard(isA(Long.class));
        cardService.unlockCard(1);
        verify(cardService, times(1)).unlockCard(1);
    }

    @Test
    public void findAllCards() {
        Collection<CardDTO> cards = Stream.of(
                new CardDTO(1L, false, 0.0, 1L, null),
                new CardDTO(2L, false, 0.0, 1L, null),
                new CardDTO(3L, false, 0.0, 2L, null))
                .collect(Collectors.toList());
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
        CardDTO cardDTO = new CardDTO(1L, false, 0.0, 1L, null);
        assertEquals(cardDTO, cardService.findById(1));
    }
}