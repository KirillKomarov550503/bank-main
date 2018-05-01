package com.netcracker.komarov.services.dto.converter;

import com.netcracker.komarov.dao.entity.Card;
import com.netcracker.komarov.services.dto.Converter;
import com.netcracker.komarov.services.dto.entity.CardDTO;
import org.springframework.stereotype.Component;

@Component
public class CardConverter implements Converter<CardDTO, Card> {
    @Override
    public CardDTO convertToDTO(Card card) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setAccountId(card.getAccount().getAccountId());
        cardDTO.setBalance(card.getAccount().getBalance());
        cardDTO.setLocked(card.isLocked());
        cardDTO.setPin(card.getPin());
        return cardDTO;
    }

    @Override
    public Card convertToEntity(CardDTO dto) {
        Card card = new Card();
        card.setPin(dto.getPin());
        card.setLocked(dto.isLocked());
        return card;
    }
}
