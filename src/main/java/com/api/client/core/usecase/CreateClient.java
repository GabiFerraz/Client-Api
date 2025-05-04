package com.api.client.core.usecase;

import com.api.client.core.domain.Client;
import com.api.client.core.dto.ClientDto;
import com.api.client.core.gateway.ClientGateway;
import com.api.client.core.usecase.exception.ClientAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateClient {

  private final ClientGateway clientGateway;

  public Client execute(final ClientDto request) {
    final var client = this.clientGateway.findByCpf(request.cpf());

    if (client.isPresent()) {
      throw new ClientAlreadyExistsException(request.cpf());
    }

    final var buildDomain =
        Client.createClient(
            request.name(),
            request.cpf(),
            request.phoneNumber(),
            request.address(),
            request.email());

    return this.clientGateway.save(buildDomain);
  }
}
