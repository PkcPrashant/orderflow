package com.orderflow.common.exception;

import com.orderflow.common.api.ApiError;
import com.orderflow.common.api.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorResponse handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        return new ValidationErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation failed",
                request.getRequestURI(),
                fieldErrors
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ApiError handleAuthenticationException(
            BadCredentialsException ex,
            HttpServletRequest request
    ) {
        String message = (ex.getMessage() == null || ex.getMessage().isBlank())
                ? "Invalid Credentials!!!"
                : ex.getMessage();

        return new ApiError(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ApiError handleDuplicateOrderException(
            DuplicateResourceException ex,
            HttpServletRequest request
    ) {
        String message = (ex.getMessage() == null || ex.getMessage().isBlank())
                ? "Resource already exists"
                : ex.getMessage();

        return new ApiError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiError handleOrderNotFoundException(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        String message = (ex.getMessage() == null || ex.getMessage().isBlank())
                ? "Resource not found"
                : ex.getMessage();

        return new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    public ApiError handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        return new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Something went wrong. Please contact support.",
                request.getRequestURI()
        );
    }
}
