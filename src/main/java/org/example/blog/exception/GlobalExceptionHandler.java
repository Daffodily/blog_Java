package org.example.blog.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.blog.dto.Response;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // DTO 校验失败 (对象参数)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationException(MethodArgumentNotValidException e) {

        log.error("parameter verification failed: {}", e.getMessage());

        FieldError fieldError = e.getBindingResult().getFieldError();

        if (fieldError != null) {
            String msg = fieldError.getDefaultMessage();
            if ("PhoneNumInvalid".equals(msg)) {
                log.error("phone number invalid");
                return Response.fail(-20001);
            }
            if ("PasswordInvalid".equals(msg)){
                log.error("password invalid");
                return Response.fail(-20002);
            }
            if ("TokenInvalid".equals(msg)){
                log.error("token invalid");
                return Response.fail(-20005);
            }
        }
        log.error("service exception");
        return Response.fail(-10000);
    }

    @ExceptionHandler(ServiceException.class)
    public Map<String, Object> handleServiceException(ServiceException e) {
        log.warn("service warning: {}", e.getMessage());
        return Response.fail(e.getCode());
    }

    @ExceptionHandler(Exception.class)
    public Map<String, Object> handleException(Exception e) {
        log.error("service exception ", e);
        return Response.fail(-10000);
    }
}
