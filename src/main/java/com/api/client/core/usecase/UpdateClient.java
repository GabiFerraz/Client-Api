package com.api.client.core.usecase;

import com.api.client.core.domain.Client;
import com.api.client.core.dto.UpdateClientDto;
import com.api.client.core.gateway.ClientGateway;
import com.api.client.core.usecase.exception.ClientNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateClient {

  private final ClientGateway clientGateway;

  public Client execute(final String cpf, final UpdateClientDto request) {
    final Client existingClient =
        clientGateway.findByCpf(cpf).orElseThrow(() -> new ClientNotFoundException(cpf));

    if (request.name() != null) existingClient.setName(request.name());

    if (request.phoneNumber() != null) existingClient.setPhoneNumber(request.phoneNumber());

    if (request.address() != null) existingClient.setAddress(request.address());

    if (request.email() != null) existingClient.setEmail(request.email());

    return clientGateway.update(existingClient);
  }
}
