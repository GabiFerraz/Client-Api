package com.api.client.core.domain;

import static com.api.client.core.domain.fixture.ClientTestFixture.clientCreate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.api.client.core.domain.exception.DomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ClientTest {

  @Test
  void shouldCreateClientSuccessfully() {
    final var client = clientCreate();

    assertThat(client.getName()).isEqualTo("Gabriela Mesquita");
    assertThat(client.getCpf()).isEqualTo("12345678900");
    assertThat(client.getPhoneNumber()).isEqualTo("84999999999");
    assertThat(client.getAddress()).isEqualTo("Rua das Flores, 123 - Centro, Natal/RN");
    assertThat(client.getEmail()).isEqualTo("gabis@example.com");
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {" "})
  void shouldNotCreateWhenNameIsBlank(final String invalidName) {
    assertThatThrownBy(
            () ->
                Client.createClient(
                    invalidName,
                    "12345678900",
                    "84999999999",
                    "Rua das Flores, 123 - Centro, Natal/RN",
                    "gabis@example.com"))
        .isInstanceOf(DomainException.class)
        .hasMessage("Field=[name] should not be empty or null by domain client");
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {"123", "123456789012345", "abcdefghijklmno"})
  void shouldNotCreateWhenCpfIsInvalid(final String invalidCpf) {
    assertThatThrownBy(
            () ->
                Client.createClient(
                    "Gabriela Mesquita",
                    invalidCpf,
                    "84999999999",
                    "Rua das Flores, 123 - Centro, Natal/RN",
                    "gabis@example.com"))
        .isInstanceOf(DomainException.class)
        .hasMessage("The field=[cpf] has an invalid pattern by domain client");
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {"123", "1234567890123456", "abcdefghijklmno"})
  void shouldNotCreateWhenPhoneNumberIsInvalid(final String invalidPhoneNumber) {
    assertThatThrownBy(
            () ->
                Client.createClient(
                    "Gabriela Mesquita",
                    "12345678900",
                    invalidPhoneNumber,
                    "Rua das Flores, 123 - Centro, Natal/RN",
                    "gabis@example.com"))
        .isInstanceOf(DomainException.class)
        .hasMessage("The field=[phoneNumber] has an invalid pattern by domain client");
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {"12345", "invalidemail", "test@", "test@.com"})
  void shouldNotCreateWhenEmailIsInvalid(final String invalidEmail) {
    assertThatThrownBy(
            () ->
                Client.createClient(
                    "Gabriela Mesquita",
                    "12345678900",
                    "84999999999",
                    "Rua das Flores, 123 - Centro, Natal/RN",
                    invalidEmail))
        .isInstanceOf(DomainException.class)
        .hasMessage("The field=[email] has an invalid pattern by domain client");
  }
}
