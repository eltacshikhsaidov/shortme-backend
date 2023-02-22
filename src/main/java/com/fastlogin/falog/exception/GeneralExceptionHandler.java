package com.fastlogin.falog.exception;

import com.fastlogin.falog.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.NoSuchElementException;

import static com.fastlogin.falog.exception.ExceptionConstants.*;
import static com.fastlogin.falog.response.Result.failed;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler({NoSuchElementException.class})
    public Result<?> handleNoSuchElementException(NoSuchElementException e) {
        log.warn("exception message: {}", e.getMessage());
        return failed(URL_NOT_FOUND, "Url not found, " + e.getMessage());
    }

    @ExceptionHandler({IOException.class})
    public Result<?> handleNoSuchElementException(IOException e) {
        log.warn("exception message: {}", e.getMessage());
        return failed(INTERNAL_SERVER_ERROR, "Could not redirect to the original url, " + e.getMessage());
    }
}
