package com.api.client.core.usecase;

import static com.api.client.core.usecase.fixture.UpdateClientTestFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.api.client.core.domain.Client;
import com.api.client.core.gateway.ClientGateway;
import com.api.client.core.usecase.exception.ClientNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class UpdateClientTest {

  private final ClientGateway clientGateway = mock(ClientGateway.class);
  private final UpdateClient updateClient = new UpdateClient(clientGateway);

  @Test
  void shouldUpdateClientSuccessfully() {
    final var cpf = "12345678900";
    final var client = validClientFound();
    final var clientUpdated = validClientUpdateResponse();
    final var request = validUpdateRequest();
    final ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);

    when(clientGateway.findByCpf(cpf)).thenReturn(Optional.of(client));
    when(clientGateway.update(clientCaptor.capture())).thenReturn(clientUpdated);

    final var response = updateClient.execute(cpf, request);

    assertThat(response).usingRecursiveComparison().isEqualTo(clientUpdated);

    verify(clientGateway).findByCpf(cpf);

    final var clientCaptured = clientCaptor.getValue();
    verify(clientGateway).update(clientCaptured);

    assertThat(clientCaptured.getId()).isEqualTo(clientUpdated.getId());
    assertThat(clientCaptured.getName()).isEqualTo(clientUpdated.getName());
    assertThat(clientCaptured.getCpf()).isEqualTo(clientUpdated.getCpf());
    assertThat(clientCaptured.getPhoneNumber()).isEqualTo(clientUpdated.getPhoneNumber());
    assertThat(clientCaptured.getAddress()).isEqualTo(clientUpdated.getAddress());
    assertThat(clientCaptured.getEmail()).isEqualTo(clientUpdated.getEmail());
  }

  @Test
  void shouldThrowClientNotFoundException() {
    final var cpf = "12345678900";
    final var request = validUpdateRequest();

    when(clientGateway.findByCpf(cpf)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> updateClient.execute(cpf, request))
        .isInstanceOf(ClientNotFoundException.class)
        .hasMessage("Client with CPF=[" + cpf + "] not found.");

    verify(clientGateway).findByCpf(cpf);
    verifyNoMoreInteractions(clientGateway);
  }
}
