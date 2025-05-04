package com.api.client.core.usecase.exception;

import static java.lang.String.format;

public class ClientAlreadyExistsException extends BusinessException {

  private static final String ERROR_CODE = "already_exists";
  private static final String MESSAGE = "Client with cpf=[%s] already exists.";

  public ClientAlreadyExistsException(final String cpf) {
    super(format(MESSAGE, cpf), ERROR_CODE);
  }
}
