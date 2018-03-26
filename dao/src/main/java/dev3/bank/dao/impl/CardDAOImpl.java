package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.CardDAO;
import dev3.bank.entity.Card;

import java.util.Collection;
import java.util.stream.Collectors;

public class CardDAOImpl extends CrudDAOImpl<Card> implements CardDAO {
    public CardDAOImpl() {
        super(Card.class);
    }

    @Override
    public Collection<Card> getLockedCards() {
        return getEntityMapValues()
                .stream()
                .filter(Card::isLocked)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Card> getUnlockedCards() {
        return getEntityMapValues()
                .stream()
                .filter(card -> !card.isLocked())
                .collect(Collectors.toList());
    }
}
