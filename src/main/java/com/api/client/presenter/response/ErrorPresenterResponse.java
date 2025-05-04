package com.api.client.presenter.response;

import lombok.Builder;

@Builder
public record ErrorPresenterResponse(String errorMessage) {}
