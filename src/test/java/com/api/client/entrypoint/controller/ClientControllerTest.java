package com.api.client.entrypoint.controller;

import static com.api.client.entrypoint.controller.fixture.ClientControllerTestFixture.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.api.client.core.dto.ClientDto;
import com.api.client.core.dto.UpdateClientDto;
import com.api.client.core.usecase.CreateClient;
import com.api.client.core.usecase.DeleteClient;
import com.api.client.core.usecase.SearchClient;
import com.api.client.core.usecase.UpdateClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ClientControllerTest {

  private static final String BASE_URL = "/api/client";
  private static final String BASE_URL_WITH_CPF = BASE_URL + "/%s";

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockitoBean private CreateClient createClient;
  @MockitoBean private SearchClient searchClient;
  @MockitoBean private UpdateClient updateClient;
  @MockitoBean private DeleteClient deleteClient;

  @Test
  void shouldCreateClientSuccessfully() throws Exception {
    final var request = validRequest();
    final var response = validResponse();

    when(createClient.execute(any(ClientDto.class))).thenReturn(response);

    mockMvc
        .perform(
            post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(response.getId()))
        .andExpect(jsonPath("$.name").value(response.getName()))
        .andExpect(jsonPath("$.cpf").value(response.getCpf()))
        .andExpect(jsonPath("$.phoneNumber").value(response.getPhoneNumber()))
        .andExpect(jsonPath("$.address").value(response.getAddress()))
        .andExpect(jsonPath("$.email").value(response.getEmail()));

    final ArgumentCaptor<ClientDto> clientCaptor = ArgumentCaptor.forClass(ClientDto.class);
    verify(createClient).execute(clientCaptor.capture());

    assertThat(clientCaptor.getValue()).usingRecursiveComparison().isEqualTo(request);
  }

  @Test
  void shouldSearchClientByCpfSuccessfully() throws Exception {
    final String cpf = "12345678900";
    final var response = validResponse();

    when(searchClient.execute(cpf)).thenReturn(Optional.of(response));

    mockMvc
        .perform(get(format(BASE_URL_WITH_CPF, cpf)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(response.getId()))
        .andExpect(jsonPath("$.name").value(response.getName()))
        .andExpect(jsonPath("$.cpf").value(response.getCpf()))
        .andExpect(jsonPath("$.phoneNumber").value(response.getPhoneNumber()))
        .andExpect(jsonPath("$.address").value(response.getAddress()))
        .andExpect(jsonPath("$.email").value(response.getEmail()));

    verify(searchClient).execute(cpf);
  }

  @Test
  void shouldSearchClientByCpfAndReturnNotFound() throws Exception {
    final String cpf = "12345678900";

    when(searchClient.execute(cpf)).thenReturn(Optional.empty());

    mockMvc
        .perform(get(format(BASE_URL_WITH_CPF, cpf)))
        .andExpect(status().isNotFound())
        .andExpect(content().json(expectedClientNotFoundByCpfResponse()));

    verify(searchClient).execute(cpf);
  }

  @Test
  void shouldUpdateClientSuccessfully() throws Exception {
    final String cpf = "12345678900";
    final var request = validUpdateRequest();
    final var response = validUpdateResponse();

    when(updateClient.execute(eq(cpf), any(UpdateClientDto.class))).thenReturn(response);

    mockMvc
        .perform(
            put(format(BASE_URL_WITH_CPF, cpf))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(response.getId()))
        .andExpect(jsonPath("$.name").value(response.getName()))
        .andExpect(jsonPath("$.cpf").value(response.getCpf()))
        .andExpect(jsonPath("$.phoneNumber").value(response.getPhoneNumber()))
        .andExpect(jsonPath("$.address").value(response.getAddress()))
        .andExpect(jsonPath("$.email").value(response.getEmail()));

    final ArgumentCaptor<UpdateClientDto> clientCaptor =
        ArgumentCaptor.forClass(UpdateClientDto.class);
    verify(updateClient).execute(eq(cpf), clientCaptor.capture());

    assertThat(clientCaptor.getValue()).usingRecursiveComparison().isEqualTo(request);
  }

  @Test
  void shouldDeleteClientSuccessfully() throws Exception {
    final String cpf = "12345678900";

    doNothing().when(deleteClient).execute(cpf);

    mockMvc.perform(delete(format(BASE_URL_WITH_CPF, cpf))).andExpect(status().isNoContent());

    verify(deleteClient).execute(cpf);
  }
}
