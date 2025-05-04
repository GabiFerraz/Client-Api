package com.api.client.core.dto;

public record ClientDto(
    String name, String cpf, String phoneNumber, String address, String email) {}
