package com.api.client.infra.gateway.fixture;

import com.api.client.core.domain.Client;
import com.api.client.infra.persistence.entity.ClientEntity;

public class ClientGatewayImplTestFixture {

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

  public static ClientEntity clientEntity() {
    return ClientEntity.builder()
        .name(NAME)
        .cpf(CPF)
        .phoneNumber(PHONE_NUMBER)
        .address(ADDRESS)
        .email(EMAIL)
        .build();
  }

  public static ClientEntity clientEntityResponse() {
    return ClientEntity.builder()
        .id(Integer.valueOf(ID))
        .name(NAME)
        .cpf(CPF)
        .phoneNumber(PHONE_NUMBER)
        .address(ADDRESS)
        .email(EMAIL)
        .build();
  }

  public static Client clientDomain() {
    return new Client(null, NAME, CPF, PHONE_NUMBER, ADDRESS, EMAIL);
  }

  public static ClientEntity clientEntityUpdate() {
    return ClientEntity.builder()
        .id(Integer.valueOf(ID))
        .name(NEW_NAME)
        .cpf(CPF)
        .phoneNumber(NEW_PHONE_NUMBER)
        .address(NEW_ADDRESS)
        .email(NEW_EMAIL)
        .build();
  }

  public static Client clientDomainUpdate() {
    return new Client(ID, NEW_NAME, CPF, NEW_PHONE_NUMBER, NEW_ADDRESS, NEW_EMAIL);
  }
}
