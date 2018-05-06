package com.netcracker.komarov.services.dto.converter;

import com.netcracker.komarov.dao.entity.Request;
import com.netcracker.komarov.services.dto.Converter;
import com.netcracker.komarov.services.dto.entity.RequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestConverter implements Converter<RequestDTO, Request> {
    private AccountConverter accountConverter;
    private CardConverter cardConverter;

    @Autowired
    public RequestConverter(AccountConverter accountConverter, CardConverter cardConverter) {
        this.accountConverter = accountConverter;
        this.cardConverter = cardConverter;
    }

    @Override
    public RequestDTO convertToDTO(Request request) {
        RequestDTO dto = null;
        if (request != null) {
            dto = new RequestDTO();
            dto.setId(request.getId());
            dto.setAccountDTO(accountConverter.convertToDTO(request.getAccount()));
            dto.setCardDTO(cardConverter.convertToDTO(request.getCard()));
        }
        return dto;
    }

    @Override
    public Request convertToEntity(RequestDTO requestDTO) {
        Request request = new Request();
        request.setAccount(accountConverter.convertToEntity(requestDTO.getAccountDTO()));
        request.setCard(cardConverter.convertToEntity(requestDTO.getCardDTO()));
        return request;
    }
}
