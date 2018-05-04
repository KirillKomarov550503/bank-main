package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Card;
import com.netcracker.komarov.dao.entity.RequestStatus;
import com.netcracker.komarov.dao.utils.DataBase;
import com.netcracker.komarov.services.interfaces.CardService;
import com.netcracker.komarov.services.interfaces.RequestService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

public class CardServiceImplTest extends AbstractSpringTest {

    @Autowired
    private CardService cardService;
    @Autowired
    private RequestService requestService;

    @Before
    public void init() {
        DataBase.initTable();
        DataBase.insertValues();
    }

    @After
    public void destroy() {
        DataBase.dropTable();
    }

    @Test
    public void lockCard() {
        Card card = new Card(2, true, 1111, 1);
        Assert.assertEquals(card, cardService.lockCard(2));
    }

    @Test
    public void createCard() {
        Card card = new Card(0, false, 2222, 0);
        Card newCard = new Card(4, false, 2222, 2);
        Assert.assertEquals(newCard, cardService.createCard(card, 2));
    }

    @Test
    public void getLockCards() {
        Collection<Card> cards = new ArrayList<>();
        Assert.assertEquals("Must return empty list", cards, cardService.getLockCards(1));
        cardService.lockCard(2);
        cardService.lockCard(3);
        cards.add(new Card(2, true, 1111, 1));
        cards.add(new Card(3, true, 4004, 1));
        Assert.assertEquals(cards, cardService.getLockCards(1));
    }

    @Test
    public void getUnlockCards() {
        Collection<Card> cards = new ArrayList<>();
        Card card2 = new Card(2, false, 1111, 1);
        Card card3 = new Card(3, false, 4004, 1);
        cards.add(card2);
        cards.add(card3);
        Assert.assertEquals(cards, cardService.getUnlockCards(1));
    }

    @Test
    public void getAllCardsByAccount() {
        Collection<Card> cards = new ArrayList<>();
        Assert.assertEquals("Must return empty list", cards, cardService.getAllCardsByAccount(2));
        cards.add(new Card(1, false, 4234, 3));
        Assert.assertEquals(cards, cardService.getAllCardsByAccount(3));
    }

    @Test
    public void getAllUnlockCardRequest() {
        Collection<Card> cards = new ArrayList<>();
        Assert.assertEquals("Must return empty list", cards, cardService.getAllUnlockCardRequest());
        cardService.lockCard(1);
        cardService.lockCard(3);
        requestService.saveRequest(1, RequestStatus.CARD);
        requestService.saveRequest(3, RequestStatus.CARD);
        Card card2 = new Card(1, true, 4234, 3);
        Card card3 = new Card(3, true, 4004, 1);
        cards.add(card2);
        cards.add(card3);
        Assert.assertEquals(cards, cardService.getAllUnlockCardRequest());
    }

    @Test
    public void unlockCard() {
        cardService.lockCard(1);
        requestService.saveRequest(1, RequestStatus.CARD);
        cardService.unlockCard(1);
        Collection<Card> accounts = new ArrayList<>();
        Card card1 = new Card(1, false, 4234, 3);
        Card card2 = new Card(2, false, 1111, 1);
        Card card3 = new Card(3, false, 4004, 1);
        accounts.add(card2);
        accounts.add(card3);
        accounts.add(card1);
        Assert.assertEquals(accounts, cardService.getAllCards());
    }

    @Test
    public void getAllCards() {
        Card card1 = new Card(1, false, 4234, 3);
        Card card2 = new Card(2, false, 1111, 1);
        Card card3 = new Card(3, false, 4004, 1);
        Collection<Card> cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        Assert.assertEquals(cards, cardService.getAllCards());
    }
}