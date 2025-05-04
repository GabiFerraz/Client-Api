package com.api.client.core.usecase;

import com.api.client.core.gateway.ClientGateway;
import com.api.client.core.usecase.exception.ClientNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteClient {

  private final ClientGateway clientGateway;

  @Transactional
  public void execute(final String cpf) {
    clientGateway.findByCpf(cpf).orElseThrow(() -> new ClientNotFoundException(cpf));
    clientGateway.deleteByCpf(cpf);
  }
}
