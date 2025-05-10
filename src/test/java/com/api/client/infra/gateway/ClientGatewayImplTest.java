package com.api.client.infra.gateway;

import static com.api.client.infra.gateway.fixture.ClientGatewayImplTestFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.api.client.infra.gateway.exception.GatewayException;
import com.api.client.infra.persistence.entity.ClientEntity;
import com.api.client.infra.persistence.repository.ClientRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class ClientGatewayImplTest {

  private final ClientRepository clientRepository = mock(ClientRepository.class);
  private final ClientGatewayImpl clientGateway = new ClientGatewayImpl(clientRepository);

  @Test
  void shouldSaveClientSuccessfully() {
    final var entity = clientEntity();
    final var entityResponse = clientEntityResponse();
    final var client = clientDomain();
    final ArgumentCaptor<ClientEntity> entityCaptor = ArgumentCaptor.forClass(ClientEntity.class);

    when(clientRepository.save(entityCaptor.capture())).thenReturn(entityResponse);

    final var response = clientGateway.save(client);

    assertThat(response.getId()).isEqualTo(entityResponse.getId().toString());
    assertThat(response.getName()).isEqualTo(entityResponse.getName());
    assertThat(response.getCpf()).isEqualTo(entityResponse.getCpf());
    assertThat(response.getPhoneNumber()).isEqualTo(entityResponse.getPhoneNumber());
    assertThat(response.getAddress()).isEqualTo(entityResponse.getAddress());
    assertThat(response.getEmail()).isEqualTo(entityResponse.getEmail());

    final var capturedEntity = entityCaptor.getValue();
    verify(clientRepository).save(capturedEntity);

    assertThat(capturedEntity.getId()).isNull();
    assertThat(capturedEntity.getName()).isEqualTo(entity.getName());
    assertThat(capturedEntity.getCpf()).isEqualTo(entity.getCpf());
    assertThat(capturedEntity.getPhoneNumber()).isEqualTo(entity.getPhoneNumber());
    assertThat(capturedEntity.getAddress()).isEqualTo(entity.getAddress());
    assertThat(capturedEntity.getEmail()).isEqualTo(entity.getEmail());
  }

  @Test
  void shouldThrowExceptionWhenOccursErrorSavingClient() {
    final var client = clientDomain();

    when(clientRepository.save(any())).thenThrow(IllegalArgumentException.class);

    assertThatThrownBy(() -> clientGateway.save(client))
        .isInstanceOf(GatewayException.class)
        .hasMessage("Error saving client with CPF=[" + client.getCpf() + "].");

    verify(clientRepository).save(any());
  }

  @Test
  void shouldFindClientSuccessfully() {
    final var cpf = "12345678900";
    final var entity = clientEntityResponse();

    when(clientRepository.findByCpf(cpf)).thenReturn(Optional.of(entity));

    final var response = clientGateway.findByCpf(cpf);

    assertThat(response).isPresent();
    assertThat(response.get().getId()).isEqualTo(entity.getId().toString());
    assertThat(response.get().getName()).isEqualTo(entity.getName());
    assertThat(response.get().getCpf()).isEqualTo(entity.getCpf());
    assertThat(response.get().getPhoneNumber()).isEqualTo(entity.getPhoneNumber());
    assertThat(response.get().getAddress()).isEqualTo(entity.getAddress());
    assertThat(response.get().getEmail()).isEqualTo(entity.getEmail());

    verify(clientRepository).findByCpf(cpf);
  }

  @Test
  void shouldReturnEmptyWhenClientNotFound() {
    final var cpf = "12345678900";

    when(clientRepository.findByCpf(cpf)).thenReturn(Optional.empty());

    final var response = clientGateway.findByCpf(cpf);

    assertThat(response).isEmpty();

    verify(clientRepository).findByCpf(cpf);
  }

  @Test
  void shouldThrowExceptionWhenOccursErrorFindingClient() {
    final var cpf = "12345678900";

    when(clientRepository.findByCpf(cpf)).thenThrow(IllegalArgumentException.class);

    assertThatThrownBy(() -> clientGateway.findByCpf(cpf))
        .isInstanceOf(GatewayException.class)
        .hasMessage("Client with CPF=[" + cpf + "] not found.");

    verify(clientRepository).findByCpf(cpf);
  }

  @Test
  void shouldUpdateClientSuccessfully() {
    final var entityFound = clientEntityResponse();
    final var entityUpdated = clientEntityUpdate();
    final var clientUpdate = clientDomainUpdate();
    final ArgumentCaptor<ClientEntity> entityCaptor = ArgumentCaptor.forClass(ClientEntity.class);

    doReturn(Optional.of(entityFound)).when(clientRepository).findByCpf(clientUpdate.getCpf());
    when(clientRepository.save(entityCaptor.capture())).thenReturn(entityUpdated);

    final var response = clientGateway.update(clientUpdate);

    assertThat(response.getId()).isEqualTo(entityUpdated.getId().toString());
    assertThat(response.getName()).isEqualTo(entityUpdated.getName());
    assertThat(response.getCpf()).isEqualTo(entityUpdated.getCpf());
    assertThat(response.getPhoneNumber()).isEqualTo(entityUpdated.getPhoneNumber());
    assertThat(response.getAddress()).isEqualTo(entityUpdated.getAddress());
    assertThat(response.getEmail()).isEqualTo(entityUpdated.getEmail());

    verify(clientRepository).findByCpf(clientUpdate.getCpf());

    final var capturedEntity = entityCaptor.getValue();
    verify(clientRepository).save(capturedEntity);

    assertThat(capturedEntity.getId()).isEqualTo(entityUpdated.getId());
    assertThat(capturedEntity.getName()).isEqualTo(entityUpdated.getName());
    assertThat(capturedEntity.getCpf()).isEqualTo(entityUpdated.getCpf());
    assertThat(capturedEntity.getPhoneNumber()).isEqualTo(entityUpdated.getPhoneNumber());
    assertThat(capturedEntity.getAddress()).isEqualTo(entityUpdated.getAddress());
    assertThat(capturedEntity.getEmail()).isEqualTo(entityUpdated.getEmail());
  }

  @Test
  void shouldThrowClientNotFoundExceptionWhenClientNotFound() {
    final var clientUpdate = clientDomainUpdate();

    when(clientRepository.findByCpf(any())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> clientGateway.update(clientUpdate))
        .isInstanceOf(GatewayException.class)
        .hasMessage("Client with CPF=[" + clientUpdate.getCpf() + "] not found.");

    verify(clientRepository).findByCpf(any());
    verifyNoMoreInteractions(clientRepository);
  }

  @Test
  void shouldThrowExceptionWhenOccursErrorUpdatingClient() {
    final var entityFound = clientEntityResponse();
    final var clientUpdate = clientDomainUpdate();

    doReturn(Optional.of(entityFound)).when(clientRepository).findByCpf(any());
    when(clientRepository.save(any())).thenThrow(IllegalArgumentException.class);

    assertThatThrownBy(() -> clientGateway.update(clientUpdate))
        .isInstanceOf(GatewayException.class)
        .hasMessage("Error updating client with CPF=[" + clientUpdate.getCpf() + "].");

    verify(clientRepository).findByCpf(any());
    verify(clientRepository).save(any());
  }

  @Test
  void shouldDeleteClientSuccessfully() {
    final var cpf = "12345678900";

    doNothing().when(clientRepository).deleteByCpf(cpf);

    clientGateway.deleteByCpf(cpf);

    verify(clientRepository).deleteByCpf(cpf);
  }

  @Test
  void shouldThrowExceptionWhenOccursErrorDeletingClient() {
    final var cpf = "12345678900";

    doThrow(IllegalArgumentException.class).when(clientRepository).deleteByCpf(cpf);

    assertThatThrownBy(() -> clientGateway.deleteByCpf(cpf))
        .isInstanceOf(GatewayException.class)
        .hasMessage("Error deleting client with CPF=[" + cpf + "].");

    verify(clientRepository).deleteByCpf(cpf);
  }
}
