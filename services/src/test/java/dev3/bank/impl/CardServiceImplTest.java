//package dev3.bank.impl;
//
//import dev3.bank.dao.utils.DataBase;
//import dev3.bank.entity.Card;
//import dev3.bank.factory.DAOFactory;
//import dev3.bank.factory.PostgreSQLDAOFactory;
//import dev3.bank.interfaces.CardService;
//import dev3.bank.interfaces.UnlockCardRequestService;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Scanner;
//
//import static org.junit.Assert.*;
//
//public class CardServiceImplTest {
//    private CardService cardService;
//    private UnlockCardRequestService cardRequestService;
//
//    @Before
//    public void init() {
//        DataBase.initTable();
//        DataBase.insertValues();
//        DAOFactory daoFactory = PostgreSQLDAOFactory.getPostgreSQLDAOFactory();
//        cardService = CardServiceImpl.getCardService();
//        cardService.setDAO(daoFactory);
//        cardRequestService = UnlockCardRequestServiceImpl.getUnlockCardRequestService();
//        cardRequestService.setDAO(daoFactory);
//    }
//
//    @After
//    public void destroy() {
//        DataBase.dropTable();
//    }
//
//    @Test
//    public void lockCard() {
//        Card card = new Card(2, true, 1111, 1, 128989);
//        Assert.assertEquals(card, cardService.lockCard(2));
//    }
//
//    @Test
//    public void createCard() {
//        Card card = new Card(0, false, 2222, 0, 900009);
//        Card newCard = new Card(4, false, 2222, 2, 900009);
//        Assert.assertEquals(newCard, cardService.createCard(card, 2));
//    }
//
//    @Test
//    public void getLockCards() {
//        Collection<Card> cards = new ArrayList<>();
//        Assert.assertEquals("Must return empty list", cards, cardService.getLockCards(1));
//        cardService.lockCard(2);
//        cardService.lockCard(3);
//        cards.add(new Card(2, true, 1111, 1, 128989));
//        cards.add(new Card(3, true, 4004, 1, 101));
//        Assert.assertEquals(cards, cardService.getLockCards(1));
//    }
//
//    @Test
//    public void getUnlockCards() {
//        Collection<Card> cards = new ArrayList<>();
//        Card card2 = new Card(2, false, 1111, 1, 128989);
//        Card card3 = new Card(3, false, 4004, 1, 101);
//        cards.add(card2);
//        cards.add(card3);
//        Assert.assertEquals(cards, cardService.getUnlockCards(1));
//    }
//
//    @Test
//    public void getAllCardsByAccount() {
//        Collection<Card> cards = new ArrayList<>();
//        Assert.assertEquals("Must return empty list", cards, cardService.getAllCardsByAccount(2));
//        cards.add(new Card(1, false, 4234, 3, 100));
//        Assert.assertEquals(cards, cardService.getAllCardsByAccount(3));
//    }
//
//    @Test
//    public void getAllUnlockCardRequest() {
//        Collection<Card> cards = new ArrayList<>();
//        Assert.assertEquals("Must return empty list", cards, cardService.getAllUnlockCardRequest());
//        cardService.lockCard(1);
//        cardService.lockCard(3);
//        cardRequestService.unlockCardRequest(1);
//        cardRequestService.unlockCardRequest(3);
//        Card card2 = new Card(1, true, 4234, 3, 100);
//        Card card3 = new Card(3, true, 4004, 1, 101);
//        cards.add(card2);
//        cards.add(card3);
//        Assert.assertEquals(cards, cardService.getAllUnlockCardRequest());
//    }
//
//    @Test
//    public void unlockCard() {
//        cardService.lockCard(1);
//        cardRequestService.unlockCardRequest(1);
//        cardService.unlockCard(1);
//        Collection<Card> accounts = new ArrayList<>();
//        Card card1 = new Card(1, false, 4234, 3, 100);
//        Card card2 = new Card(2, false, 1111, 1, 128989);
//        Card card3 = new Card(3, false, 4004, 1, 101);
//        accounts.add(card2);
//        accounts.add(card3);
//        accounts.add(card1);
//        Assert.assertEquals(accounts, cardService.getAllCards());
//    }
//
//    @Test
//    public void getAllCards() {
//        Card card1 = new Card(1, false, 4234, 3, 100);
//        Card card2 = new Card(2, false, 1111, 1, 128989);
//        Card card3 = new Card(3, false, 4004, 1, 101);
//        Collection<Card> cards = new ArrayList<>();
//        cards.add(card1);
//        cards.add(card2);
//        cards.add(card3);
//        Assert.assertEquals(cards, cardService.getAllCards());
//    }
//}