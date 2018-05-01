package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Card;
import com.netcracker.komarov.dao.entity.UnlockCardRequest;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.CardRepository;
import com.netcracker.komarov.dao.repository.UnlockCardRequestRepository;
import com.netcracker.komarov.services.dto.converter.UnlockCardRequestConverter;
import com.netcracker.komarov.services.dto.entity.UnlockCardRequestDTO;
import com.netcracker.komarov.services.interfaces.UnlockCardRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UnlockCardRequestServiceImpl implements UnlockCardRequestService {
    private UnlockCardRequestRepository unlockCardRequestRepository;
    private CardRepository cardRepository;
    private UnlockCardRequestConverter requestConverter;
    private Logger logger = LoggerFactory.getLogger(UnlockCardRequestServiceImpl.class);

    @Autowired
    public UnlockCardRequestServiceImpl(RepositoryFactory repositoryFactory, UnlockCardRequestConverter requestConverter) {
        this.unlockCardRequestRepository = repositoryFactory.getUnlockCardRequestRepository();
        this.cardRepository = repositoryFactory.getCardRepository();
        this.requestConverter = requestConverter;
    }

    private Collection<UnlockCardRequestDTO> convertCollection(Collection<UnlockCardRequest> requests) {
        return requests.stream()
                .map(request -> requestConverter.convertToDTO(request))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Collection<UnlockCardRequestDTO> getAllCardRequest() {
        logger.info("Return all requests to unlock cards");
        return convertCollection(unlockCardRequestRepository.findAll());
    }

    @Transactional
    @Override
    public UnlockCardRequestDTO addCardRequest(long cardId) {
        UnlockCardRequest temp = null;
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            if (unlockCardRequestRepository.longByCardId(cardId) == null) {
                Card card = optionalCard.get();
                UnlockCardRequest request = new UnlockCardRequest();
                request.setCard(card);
                card.setUnlockCardRequest(request);
                temp = unlockCardRequestRepository.save(request);
                logger.info("Sending request to unlock card");
            } else {
                logger.info("You had already sent request to unlock card");
            }
        } else {
            logger.info("There is no such card in database");
        }
        return requestConverter.convertToDTO(temp);
    }
}
