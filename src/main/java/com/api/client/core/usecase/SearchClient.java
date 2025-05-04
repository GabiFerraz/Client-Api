package com.api.client.core.usecase;

import com.api.client.core.domain.Client;
import com.api.client.core.gateway.ClientGateway;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchClient {

  private final ClientGateway clientGateway;

  public Optional<Client> execute(final String cpf) {
    return clientGateway.findByCpf(cpf);
  }
}
