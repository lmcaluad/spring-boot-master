package com.curso.master.exception;

import com.curso.master.dto.response.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpRequestMethodNotSupportedException.class,
            NoHandlerFoundException.class,
            ResponseStatusException.class,
            MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class,
            NoSuchElementException.class,
            ObjectNotFoundException.class,
            InvalidPasswordException.class,
            Exception.class,
            DuplicateRatingException.class
    })
    public ResponseEntity<ApiError> handleAllExceptions(
            Exception exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        ZoneId zoneId = ZoneId.of("America/Lima");
        LocalDateTime timestamp = LocalDateTime.now(zoneId);

        if (exception instanceof HttpMessageNotReadableException)
            return this.handleHttpMessageNotReadableException((HttpMessageNotReadableException) exception, request, response, timestamp);
        else if (exception instanceof HttpMediaTypeNotSupportedException)
            return this.handleHttpMediaTypeNotSupportedException((HttpMediaTypeNotSupportedException) exception, request, response, timestamp);
        else if (exception instanceof HttpRequestMethodNotSupportedException)
            return this.handleHttpRequestMethodNotSupportedException((HttpRequestMethodNotSupportedException) exception, request, response, timestamp);
        else if (exception instanceof NoHandlerFoundException)
            return this.handleNoHandlerFoundException((NoHandlerFoundException) exception, request, response, timestamp);
        else if (exception instanceof ResponseStatusException)
            return this.handleResponseStatusException((ResponseStatusException) exception, request, response, timestamp);
        else if (exception instanceof MethodArgumentTypeMismatchException)
            return this.handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) exception, request, response, timestamp);
        else if (exception instanceof MethodArgumentNotValidException)
            return this.handleMethodArgumentNotValidException((MethodArgumentNotValidException) exception, request, response, timestamp);
        else if (exception instanceof NoSuchElementException)
            return this.handleNoSuchElementException((NoSuchElementException) exception, request, response, timestamp);
        else if (exception instanceof ObjectNotFoundException)
            return this.handleObjectNotFoundException((ObjectNotFoundException) exception, request, response, timestamp);
        else if (exception instanceof InvalidPasswordException)
            return this.handleInvalidPasswordException((InvalidPasswordException) exception, request, response, timestamp);
        else if (exception instanceof DuplicateRatingException)
            return this.handleInvalidDuplicateRatingException((DuplicateRatingException) exception, request, response, timestamp);
        else
            return this.handleException(exception, request, response, timestamp);
    }

    private ResponseEntity<ApiError> handleInvalidDuplicateRatingException(
            DuplicateRatingException exceptionMessage, HttpServletRequest request,
            HttpServletResponse response, LocalDateTime timestamp)
    {
        int httpStatus = HttpStatus.CONFLICT.value();

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                exceptionMessage.getMessage(),
                exceptionMessage.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleException(
            Exception exception, HttpServletRequest request,
            HttpServletResponse response, LocalDateTime timestamp)
    {
        int httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String errorMessage = "An unexpected error occurred.";

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                errorMessage,
                exception.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exceptionMessage, HttpServletRequest request,
            HttpServletResponse response, LocalDateTime timestamp)
    {
        int httpStatus = HttpStatus.BAD_REQUEST.value();

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Malformed JSON request.",
                exceptionMessage.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException exceptionMessage, HttpServletRequest request,
            HttpServletResponse response, LocalDateTime timestamp)
    {
        int httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE.value();

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Unsupported Media Type. Expected application/json.",
                exceptionMessage.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exceptionMessage, HttpServletRequest request,
            HttpServletResponse response, LocalDateTime timestamp)
    {
        int httpStatus = HttpStatus.METHOD_NOT_ALLOWED.value();

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "The HTTP method is not supported for this endpoint.",
                exceptionMessage.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleNoHandlerFoundException(
            NoHandlerFoundException exceptionMessage, HttpServletRequest request,
            HttpServletResponse response, LocalDateTime timestamp)
    {
        int httpStatus = HttpStatus.NOT_FOUND.value();

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "No handler found for the requested URL.",
                exceptionMessage.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleResponseStatusException(
            ResponseStatusException exceptionMessage, HttpServletRequest request,
            HttpServletResponse response, LocalDateTime timestamp)
    {
        int httpStatus = exceptionMessage.getBody().getStatus();

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                exceptionMessage.getReason(),
                exceptionMessage.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exceptionMessage, HttpServletRequest request,
            HttpServletResponse response, LocalDateTime timestamp)
    {
        int httpStatus = HttpStatus.BAD_REQUEST.value();

        String errorMessage = String.format("Incorrect type for parameter '%s'. Expected '%s', but got '%s'.",
                exceptionMessage.getName(),
                exceptionMessage.getRequiredType().getSimpleName(),
                exceptionMessage.getValue() != null ? exceptionMessage.getValue().getClass().getSimpleName() : "null");

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                errorMessage,
                exceptionMessage.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exceptionMessage, HttpServletRequest request,
            HttpServletResponse response, LocalDateTime timestamp)
    {
        int httpStatus = HttpStatus.BAD_REQUEST.value();

        List<ObjectError> errors = exceptionMessage.getAllErrors();
        List<String> details = errors.stream().map(error -> {
            if (error instanceof FieldError fieldError)
                return fieldError.getField() + ": " + fieldError.getDefaultMessage();
            return error.getDefaultMessage();
        }).toList();

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "The request contains invalid or incomplete parameters. Please verify and provide the required information before trying again.",
                exceptionMessage.getMessage(),
                timestamp,
                details
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleNoSuchElementException(
            NoSuchElementException exceptionMessage, HttpServletRequest request,
            HttpServletResponse response, LocalDateTime timestamp)
    {
        int httpStatus = HttpStatus.NOT_FOUND.value();

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "No element found.",
                exceptionMessage.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleObjectNotFoundException(
            ObjectNotFoundException exceptionMessage, HttpServletRequest request,
            HttpServletResponse response, LocalDateTime timestamp)
    {
        int httpStatus = HttpStatus.NOT_FOUND.value();

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "The requested object was not found.",
                exceptionMessage.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    private ResponseEntity<ApiError> handleInvalidPasswordException(
            InvalidPasswordException exceptionMessage, HttpServletRequest request,
            HttpServletResponse response, LocalDateTime timestamp)
    {
        int httpStatus = HttpStatus.UNAUTHORIZED.value();

        ApiError apiError = new ApiError(
                httpStatus,
                request.getRequestURL().toString(),
                request.getMethod(),
                "Invalid password provided.",
                exceptionMessage.getMessage(),
                timestamp,
                null
        );
        return ResponseEntity.status(httpStatus).body(apiError);
    }
}
