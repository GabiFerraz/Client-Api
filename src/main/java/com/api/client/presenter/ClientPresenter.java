package com.api.client.presenter;

import com.api.client.core.domain.Client;
import com.api.client.presenter.response.ClientPresenterResponse;
import org.springframework.stereotype.Component;

@Component
public class ClientPresenter {

  public ClientPresenterResponse parseToResponse(final Client client) {
    return ClientPresenterResponse.builder()
        .id(Integer.parseInt(client.getId()))
        .name(client.getName())
        .cpf(client.getCpf())
        .phoneNumber(client.getPhoneNumber())
        .address(client.getAddress())
        .email(client.getEmail())
        .build();
  }
}
