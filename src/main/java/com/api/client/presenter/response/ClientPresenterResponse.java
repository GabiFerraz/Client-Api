package com.api.client.presenter.response;

import lombok.Builder;

@Builder
public record ClientPresenterResponse(
    int id, String name, String cpf, String phoneNumber, String address, String email) {}
