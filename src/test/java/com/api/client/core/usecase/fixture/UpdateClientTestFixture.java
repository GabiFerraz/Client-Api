package com.api.client.core.usecase.fixture;

import com.api.client.core.domain.Client;
import com.api.client.core.dto.UpdateClientDto;

public class UpdateClientTestFixture {

  private static final String ID = "1";
  private static final String NAME = "Gabriela Mesquita";
  private static final String CPF = "12345678900";
  private static final String PHONE_NUMBER = "84999999999";
  private static final String ADDRESS = "Rua das Flores, 123 - Centro, Natal/RN";
  private static final String EMAIL = "gabis@example.com";
  private static final String NEW_NAME = "Gabriela Machado de Mesquita";
  private static final String NEW_PHONE_NUMBER = "84888888888";
  private static final String NEW_ADDRESS = "Rua das Rosas, 123 - Ponta Negra, Natal/RN";
  private static final String NEW_EMAIL = "gabis@test.com";

  public static UpdateClientDto validUpdateRequest() {
    return new UpdateClientDto(NEW_NAME, NEW_PHONE_NUMBER, NEW_ADDRESS, NEW_EMAIL);
  }

  public static Client validClientFound() {
    return new Client(ID, NAME, CPF, PHONE_NUMBER, ADDRESS, EMAIL);
  }

  public static Client validClientUpdateResponse() {
    return new Client(ID, NEW_NAME, CPF, NEW_PHONE_NUMBER, NEW_ADDRESS, NEW_EMAIL);
  }
}
