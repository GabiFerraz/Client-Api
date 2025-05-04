package com.api.client.core.usecase;

import static com.api.client.core.usecase.fixture.CreateClientTestFixture.validClientRequest;
import static com.api.client.core.usecase.fixture.CreateClientTestFixture.validClientResponse;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.api.client.core.domain.Client;
import com.api.client.core.gateway.ClientGateway;
import com.api.client.core.usecase.exception.ClientAlreadyExistsException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class CreateClientTest {

  private final ClientGateway clientGateway = mock(ClientGateway.class);
  private final CreateClient createClient = new CreateClient(clientGateway);

  @Test
  void shouldCreateClientSuccessfully() {
    final var request = validClientRequest();
    final var gatewayResponse = validClientResponse();
    final ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);

    when(clientGateway.findByCpf(any())).thenReturn(empty());
    when(clientGateway.save(clientCaptor.capture())).thenReturn(gatewayResponse);

    final var response = createClient.execute(request);

    assertThat(response).usingRecursiveComparison().isEqualTo(gatewayResponse);

    verify(clientGateway).findByCpf(any());

    final var clientCaptured = clientCaptor.getValue();
    verify(clientGateway).save(clientCaptured);

    assertThat(clientCaptured.getId()).isNull();
    assertThat(clientCaptured.getName()).isEqualTo(request.name());
    assertThat(clientCaptured.getCpf()).isEqualTo(request.cpf());
    assertThat(clientCaptured.getPhoneNumber()).isEqualTo(request.phoneNumber());
    assertThat(clientCaptured.getAddress()).isEqualTo(request.address());
    assertThat(clientCaptured.getEmail()).isEqualTo(request.email());
  }

  @Test
  void shouldThrowExceptionWhenClientAlreadyExists() {
    final var request = validClientRequest();
    final var gatewayResponse = validClientResponse();

    when(clientGateway.findByCpf(request.cpf())).thenReturn(Optional.of(gatewayResponse));

    assertThatThrownBy(() -> createClient.execute(request))
        .isInstanceOf(ClientAlreadyExistsException.class)
        .hasMessage("Client with cpf=[" + request.cpf() + "] already exists.");

    verify(clientGateway).findByCpf(request.cpf());
    verifyNoMoreInteractions(clientGateway);
  }
}
