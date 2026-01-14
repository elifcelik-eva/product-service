package com.elifcelik.stockmanagement.productservice.exception.enums.handler;

import com.elifcelik.stockmanagement.productservice.exception.enums.FriendlyMessageCodes;
import com.elifcelik.stockmanagement.productservice.exception.enums.exceptions.ProductNotCreateException;
import com.elifcelik.stockmanagement.productservice.exception.enums.utils.FriendlyMessageUtils;
import com.elifcelik.stockmanagement.productservice.response.FriendlyMessage;
import com.elifcelik.stockmanagement.productservice.response.InternalApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClient;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProductNotCreateException.class)
    public InternalApiResponse<String> handleProductNotCreatedException(ProductNotCreateException exception){
        return InternalApiResponse.<String>builder()
                .message(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                         .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .build();
    }
}
