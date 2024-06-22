package com.example.nutrimatebackend.middleware;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Slf4j
@Component
@Order(1)
public class FirebaseAuthFilter implements Filter {
    private FirebaseToken decodeToken(String token) {
        FirebaseToken decodedToken;

        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
        } catch (FirebaseAuthException exception) {
            log.error(exception.getMessage());

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Can't verify token!"
            );
        }

        if (decodedToken == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Token is invalid!"
            );
        }

        return decodedToken;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String token = httpRequest.getHeader("Authorization");

        if (token == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Token is missing!"
            );
        }

        FirebaseToken decodedToken = decodeToken(token);

        String email = decodedToken.getEmail();
        request.setAttribute("email", email);

        chain.doFilter(request, response);
    }
}
