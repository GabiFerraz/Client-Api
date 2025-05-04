package com.api.client.core.usecase;

import static com.api.client.core.usecase.fixture.SearchClientTestFixture.validClientResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.api.client.core.gateway.ClientGateway;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class SearchClientTest {

  private final ClientGateway clientGateway = mock(ClientGateway.class);
  private final SearchClient searchClient = new SearchClient(clientGateway);

  @Test
  void shouldSearchClientByCpfSuccessfully() {
    final var cpf = "12345678900";
    final var client = validClientResponse();

    when(clientGateway.findByCpf(cpf)).thenReturn(Optional.of(client));

    final var response = searchClient.execute(cpf);

    assertThat(response.get()).usingRecursiveComparison().isEqualTo(client);

    verify(clientGateway).findByCpf(cpf);
  }

  @Test
  void shouldReturnEmptyWhenClientNotFound() {
    final var cpf = "12345678900";

    when(clientGateway.findByCpf(cpf)).thenReturn(Optional.empty());

    final var response = searchClient.execute(cpf);

    assertThat(response).isEmpty();

    verify(clientGateway).findByCpf(cpf);
  }
}
