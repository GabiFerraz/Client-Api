package com.api.client.core.domain;

import static java.lang.String.format;

import com.api.client.core.domain.exception.DomainException;
import com.api.client.core.domain.valueobject.ValidationDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Client {

  private static final String DOMAIN_MESSAGE_ERROR = "by domain client";
  private static final String BLANK_MESSAGE_ERROR = "Field=[%s] should not be empty or null";
  private static final String PATTERN_ERROR_MESSAGE = "The field=[%s] has an invalid pattern";
  private static final Predicate<String> PATTERN_CPF =
      n -> n == null || !Pattern.compile("\\d{11}").matcher(n).matches();
  private static final Predicate<String> PATTERN_PHONE_NUMBER =
      n -> n == null || !Pattern.compile("\\+?\\d{10,15}").matcher(n).matches();
  private static final Predicate<String> PATTERN_EMAIL =
      n -> n == null || !Pattern.compile("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$").matcher(n).matches();

  private String id;
  private String name;
  private String cpf;
  private String phoneNumber;
  private String address;
  private String email;

  public Client() {}

  public Client(
      final String id,
      final String name,
      final String cpf,
      final String phoneNumber,
      final String address,
      final String email) {

    validateDomain(name, cpf, phoneNumber, address, email);

    this.id = id;
    this.name = name;
    this.cpf = cpf;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.email = email;
  }

  public static Client createClient(
      final String name,
      final String cpf,
      final String phoneNumber,
      final String address,
      final String email) {

    validateDomain(name, cpf, phoneNumber, address, email);

    return new Client(null, name, cpf, phoneNumber, address, email);
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getCpf() {
    return cpf;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getAddress() {
    return address;
  }

  public String getEmail() {
    return email;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setPhoneNumber(final String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setAddress(final String address) {
    this.address = address;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  private static void validateDomain(
      final String name,
      final String cpf,
      final String phoneNumber,
      final String address,
      final String email) {

    final List<ValidationDomain<?>> rules =
        List.of(
            new ValidationDomain<>(
                name,
                format(BLANK_MESSAGE_ERROR, "name"),
                List.of(Objects::isNull, String::isBlank)),
            new ValidationDomain<>(cpf, format(PATTERN_ERROR_MESSAGE, "cpf"), List.of(PATTERN_CPF)),
            new ValidationDomain<>(
                phoneNumber,
                format(PATTERN_ERROR_MESSAGE, "phoneNumber"),
                List.of(PATTERN_PHONE_NUMBER)),
            new ValidationDomain<>(
                address,
                format(BLANK_MESSAGE_ERROR, "address"),
                List.of(Objects::isNull, String::isBlank)),
            new ValidationDomain<>(
                email, format(PATTERN_ERROR_MESSAGE, "email"), List.of(PATTERN_EMAIL)));

    final var errors = validate(rules);

    if (!errors.isEmpty()) {
      throw new DomainException(errors);
    }
  }

  private static List<String> validate(final List<ValidationDomain<?>> validations) {
    return validations.stream()
        .filter(Client::isInvalid)
        .map(it -> format("%s %s", it.message(), DOMAIN_MESSAGE_ERROR))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private static <T> boolean isInvalid(final ValidationDomain<T> domain) {
    return domain.predicates().stream().anyMatch(predicate -> predicate.test(domain.field()));
  }
}
