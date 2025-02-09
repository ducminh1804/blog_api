package com.ducminh.blogapi.exception;

import com.ducminh.blogapi.dto.response.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //handle runtimeException
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ApiResponse apiResponse = new ApiResponse();
        ErrorCode errorCode = exception.getErrorCode();
        apiResponse.setMessage(errorCode.getMessage());
        apiResponse.setCode(errorCode.getCode());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    //handle validation
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleValidationExceptions(
            MethodArgumentNotValidException exception) {
        ApiResponse apiResponse = new ApiResponse();
        StringBuilder errors = new StringBuilder();
        List<ObjectError> objs = exception.getBindingResult().getAllErrors();
        ObjectError objectError = objs.get(0);
        String fieldError = ((FieldError) objectError).getField();
        String errorMessage = objectError.getDefaultMessage();
        errors.append(fieldError + "-" + errorMessage);
        apiResponse.setCode(ErrorCode.INVALID_DATA.getCode());
        apiResponse.setMessage(errors.toString());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SignatureException.class)
    ResponseEntity<ApiResponse> handlingSignatureException(SignatureException ex) {
        return new ResponseEntity<>(ApiResponse.builder()
                .code(ErrorCode.INVALID_SIGNATURE.getCode())
                .message(ErrorCode.INVALID_SIGNATURE.getMessage())
                .build(),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(ApiResponse.builder()
                .code(ErrorCode.ACCESSDINED.getCode())
                .message(ErrorCode.ACCESSDINED.getMessage())
                .build(),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    ResponseEntity<ApiResponse> handlingExpiredJwtException(ExpiredJwtException ex) {
        return new ResponseEntity<>(ApiResponse.builder()
                .code(ErrorCode.EXPIRED_JWT.getCode())
                .message(ErrorCode.EXPIRED_JWT.getMessage())
                .build(),
                HttpStatus.UNAUTHORIZED);
    }
}
