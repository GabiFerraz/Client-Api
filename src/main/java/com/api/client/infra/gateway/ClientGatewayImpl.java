package com.api.client.infra.gateway;

import static java.lang.String.format;

import com.api.client.core.domain.Client;
import com.api.client.core.gateway.ClientGateway;
import com.api.client.infra.gateway.exception.GatewayException;
import com.api.client.infra.persistence.entity.ClientEntity;
import com.api.client.infra.persistence.repository.ClientRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientGatewayImpl implements ClientGateway {

  private static final String SAVE_ERROR_MESSAGE = "Error saving client with CPF=[%s].";
  private static final String FIND_ERROR_MESSAGE = "Client with CPF=[%s] not found.";
  private static final String UPDATE_ERROR_MESSAGE = "Error updating client with CPF=[%s].";
  private static final String DELETE_ERROR_MESSAGE = "Error deleting client with CPF=[%s].";

  private final ClientRepository clientRepository;

  @Override
  public Client save(final Client client) {
    try {
      final var entity =
          ClientEntity.builder()
              .name(client.getName())
              .cpf(client.getCpf())
              .phoneNumber(client.getPhoneNumber())
              .address(client.getAddress())
              .email(client.getEmail())
              .build();

      final var saved = clientRepository.save(entity);

      return this.toResponse(saved);
    } catch (IllegalArgumentException e) {
      throw new GatewayException(format(SAVE_ERROR_MESSAGE, client.getCpf()));
    }
  }

  @Override
  public Optional<Client> findByCpf(final String cpf) {
    try {
      final var entity = clientRepository.findByCpf(cpf);

      return entity.map(this::toResponse);
    } catch (IllegalArgumentException e) {
      throw new GatewayException(format(FIND_ERROR_MESSAGE, cpf));
    }
  }

  @Override
  public Client update(final Client client) {
    try {
      final var entity =
          clientRepository
              .findByCpf(client.getCpf())
              .orElseThrow(() -> new GatewayException(format(FIND_ERROR_MESSAGE, client.getCpf())));

      entity.setName(client.getName());
      entity.setPhoneNumber(client.getPhoneNumber());
      entity.setAddress(client.getAddress());
      entity.setEmail(client.getEmail());

      final var updatedEntity = clientRepository.save(entity);

      return this.toResponse(updatedEntity);
    } catch (IllegalArgumentException e) {
      throw new GatewayException(format(UPDATE_ERROR_MESSAGE, client.getCpf()));
    }
  }

  @Override
  public void deleteByCpf(final String cpf) {
    try {
      clientRepository.deleteByCpf(cpf);
    } catch (IllegalArgumentException e) {
      throw new GatewayException(format(DELETE_ERROR_MESSAGE, cpf));
    }
  }

  private Client toResponse(final ClientEntity entity) {
    return new Client(
        entity.getId().toString(),
        entity.getName(),
        entity.getCpf(),
        entity.getPhoneNumber(),
        entity.getAddress(),
        entity.getEmail());
  }
}
