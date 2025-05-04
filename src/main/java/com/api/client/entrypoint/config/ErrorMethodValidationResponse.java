package com.api.client.entrypoint.config;

import java.util.List;

public record ErrorMethodValidationResponse(String errorCode, List<String> violations) {}
