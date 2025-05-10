package com.api.client.core.usecase;

import static com.api.client.core.usecase.fixture.DeleteClientTestFixture.validClientResponse;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.api.client.core.gateway.ClientGateway;
import com.api.client.core.usecase.exception.ClientNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class DeleteClientTest {

  private final ClientGateway clientGateway = mock(ClientGateway.class);
  private final DeleteClient deleteClient = new DeleteClient(clientGateway);

  @Test
  void shouldDeleteClientSuccessfully() {
    final String cpf = "12345678900";
    final var client = validClientResponse();

    when(clientGateway.findByCpf(cpf)).thenReturn(Optional.of(client));
    doNothing().when(clientGateway).deleteByCpf(cpf);

    deleteClient.execute(cpf);

    verify(clientGateway).findByCpf(cpf);
    verify(clientGateway).deleteByCpf(cpf);
  }

  @Test
  void shouldThrowExceptionWhenClientNotFound() {
    final String cpf = "12345678900";

    when(clientGateway.findByCpf(cpf)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> deleteClient.execute(cpf))
        .isInstanceOf(ClientNotFoundException.class)
        .hasMessage("Client with CPF=[" + cpf + "] not found.");

    verify(clientGateway).findByCpf(cpf);
    verifyNoMoreInteractions(clientGateway);
  }
}
