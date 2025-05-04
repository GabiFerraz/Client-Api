package com.api.client.entrypoint.controller.fixture;

import com.api.client.core.domain.Client;
import com.api.client.core.dto.ClientDto;
import com.api.client.core.dto.UpdateClientDto;

public class ClientControllerTestFixture {

  private static final String ID = "1";
  private static final String NAME = "Gabriela";
  private static final String CPF = "12345678900";
  private static final String PHONE_NUMBER = "84999999999";
  private static final String ADDRESS = "Rua das Flores, 123, Ponta Negra, Natal/RN";
  private static final String EMAIL = "gabis@example.com";
  private static final String NEW_NAME = "Gabriela Mesquita";
  private static final String NEW_PHONE_NUMBER = "84888888888";
  private static final String NEW_ADDRESS = "Rua das Laranjeiras, 321, Nova Parnamirim, Natal/RN";
  private static final String NEW_EMAIL = "gabis@test.com";

  public static ClientDto validRequest() {
    return new ClientDto(NAME, CPF, PHONE_NUMBER, ADDRESS, EMAIL);
  }

  public static Client validResponse() {
    return new Client(ID, NAME, CPF, PHONE_NUMBER, ADDRESS, EMAIL);
  }

  public static String expectedClientNotFoundByCpfResponse() {
    return """
            {
                "errorMessage": "Client with CPF=[12345678900] not found"
            }
            """;
  }

  public static UpdateClientDto validUpdateRequest() {
    return new UpdateClientDto(NEW_NAME, NEW_PHONE_NUMBER, NEW_ADDRESS, NEW_EMAIL);
  }

  public static Client validUpdateResponse() {
    return new Client(ID, NEW_NAME, CPF, NEW_PHONE_NUMBER, NEW_ADDRESS, NEW_EMAIL);
  }
}
