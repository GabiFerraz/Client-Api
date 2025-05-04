package com.api.client.entrypoint.config;

import java.util.Set;

public record ViolationErrorResponse(String error, Set<Violation> violations) {}
