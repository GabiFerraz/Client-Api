package com.api.client.core.domain.exception;

public record ErrorDetail(String field, String errorMessage, Object rejectedValue) {}
