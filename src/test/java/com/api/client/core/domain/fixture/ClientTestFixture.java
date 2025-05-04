package com.api.client.core.domain.fixture;

import com.api.client.core.domain.Client;

public class ClientTestFixture {

  private static final String NAME = "Gabriela Mesquita";
  private static final String CPF = "12345678900";
  private static final String PHONE_NUMBER = "84999999999";
  private static final String ADDRESS = "Rua das Flores, 123 - Centro, Natal/RN";
  private static final String EMAIL = "gabis@example.com";

  public static Client clientCreate() {
    return Client.createClient(NAME, CPF, PHONE_NUMBER, ADDRESS, EMAIL);
  }
}
