package dev3.bank.dao;

import dev3.bank.entity.Card;

import java.util.Collection;

public interface CardDAO extends CrudDAO<Card> {
    Collection<Card> getLockedCards();
    Collection<Card> getUnlockedCards();
}
