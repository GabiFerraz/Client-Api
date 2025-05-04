package com.api.client.core.gateway;

import com.api.client.core.domain.Client;
import java.util.Optional;

public interface ClientGateway {

  Client save(final Client client);

  Optional<Client> findByCpf(final String cpf);

  Client update(final Client client);

  void deleteByCpf(final String cpf);
}
