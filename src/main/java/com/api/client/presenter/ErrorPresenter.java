package com.api.client.presenter;

import com.api.client.presenter.response.ErrorPresenterResponse;
import org.springframework.stereotype.Component;

@Component
public class ErrorPresenter {

  public ErrorPresenterResponse toPresenterErrorResponse(final String errorMessage) {
    return ErrorPresenterResponse.builder().errorMessage(errorMessage).build();
  }
}
