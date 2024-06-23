package com.example.nutrimatebackend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.server.ResponseStatusException;

@Component
public class HttpRequestUtil {
    public String getUserEmailFromRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Can't get attributes from the request"
            );
        }

        Object emailValue = attributes.getAttribute(
                "email",
                RequestAttributes.SCOPE_REQUEST
        );

        if (emailValue == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Can't get email from the request"
            );
        }

        if (!(emailValue instanceof String)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Email is not a String"
            );
        }

        return (String) emailValue;
    }
}
