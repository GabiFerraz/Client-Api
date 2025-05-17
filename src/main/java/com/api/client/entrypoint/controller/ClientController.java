package com.api.client.entrypoint.controller;

import static java.lang.String.format;

import com.api.client.core.domain.Client;
import com.api.client.core.dto.ClientDto;
import com.api.client.core.dto.UpdateClientDto;
import com.api.client.core.usecase.CreateClient;
import com.api.client.core.usecase.DeleteClient;
import com.api.client.core.usecase.SearchClient;
import com.api.client.core.usecase.UpdateClient;
import com.api.client.presenter.ClientPresenter;
import com.api.client.presenter.ErrorPresenter;
import com.api.client.presenter.response.ClientPresenterResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
public class ClientController {

  private static final String CLIENT_NOT_FOUND_MESSAGE = "Client with CPF=[%s] not found";

  private final CreateClient createClient;
  private final SearchClient searchClient;
  private final UpdateClient updateClient;
  private final DeleteClient deleteClient;
  private final ClientPresenter clientPresenter;
  private final ErrorPresenter errorPresent;

  @PostMapping
  public ResponseEntity<ClientPresenterResponse> create(@Valid @RequestBody final Client request) {
    final var client = this.createClient.execute(this.toClientDto(request));

    return new ResponseEntity<>(clientPresenter.parseToResponse(client), HttpStatus.CREATED);
  }

  @GetMapping("/{cpf}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Object> search(
      @Validated
          @NotBlank(message = "The field 'cpf' is required")
          @Pattern(regexp = "\\d{11}", message = "The field 'cpf' must be 11 digits")
          @PathVariable("cpf")
          final String cpf) {
    final var response = this.searchClient.execute(cpf);

    return response
        .<ResponseEntity<Object>>map(
            client -> ResponseEntity.ok(clientPresenter.parseToResponse(client)))
        .orElseGet(
            () ->
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                        errorPresent.toPresenterErrorResponse(
                            format(CLIENT_NOT_FOUND_MESSAGE, cpf))));
  }

  @PutMapping("/{cpf}")
  public ResponseEntity<ClientPresenterResponse> update(
      @Validated
          @NotBlank(message = "The field 'cpf' is required")
          @Pattern(regexp = "\\d{11}", message = "The field 'cpf' must be 11 digits")
          @PathVariable
          final String cpf,
      @Valid @RequestBody final UpdateClientDto request) {
    final var client = this.updateClient.execute(cpf, request);

    return new ResponseEntity<>(clientPresenter.parseToResponse(client), HttpStatus.OK);
  }

  @DeleteMapping("/{cpf}")
  public ResponseEntity<Void> delete(@PathVariable final String cpf) {
    this.deleteClient.execute(cpf);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  private ClientDto toClientDto(final Client client) {
    return new ClientDto(
        client.getName(),
        client.getCpf(),
        client.getPhoneNumber(),
        client.getAddress(),
        client.getEmail());
  }
}
