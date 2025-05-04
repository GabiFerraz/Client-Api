package com.api.client.core.usecase.fixture;

import com.api.client.core.domain.Client;

public class DeleteClientTestFixture {
  private static final String ID = "1";
  private static final String NAME = "Gabriela Mesquita";
  private static final String CPF = "12345678900";
  private static final String PHONE_NUMBER = "84999999999";
  private static final String ADDRESS = "Rua das Flores, 123 - Centro, Natal/RN";
  private static final String EMAIL = "gabis@example.com";

  public static Client validClientResponse() {
    return new Client(ID, NAME, CPF, PHONE_NUMBER, ADDRESS, EMAIL);
  }
}
