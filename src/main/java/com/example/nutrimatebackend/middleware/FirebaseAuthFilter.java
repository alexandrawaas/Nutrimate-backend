package com.example.nutrimatebackend.middleware;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Slf4j
@Component
@Order(1)
public class FirebaseAuthFilter implements Filter {
    private final Environment env;

    public FirebaseAuthFilter(Environment env) {
        this.env = env;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String isInDebugMode = env.getProperty("DEBUG");

        // If authorization is disabled, use a dummy email for testing and continue
        if (isInDebugMode != null && isInDebugMode.equals("true")) {
            request.setAttribute("email", "timwagner997@gmail.com");
            chain.doFilter(request, response);

            return;
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = httpRequest.getHeader("Authorization");

        if (token == null) {
            httpResponse.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Token is missing."
            );

            return;
        }

        FirebaseToken decodedToken;

        try {
            decodedToken = decodeToken(token);
        } catch (ResponseStatusException exception) {
            httpResponse.sendError(
                    exception.getStatusCode().value(),
                    exception.getReason()
            );

            return;
        }

        String email = decodedToken.getEmail();
        request.setAttribute("email", email);
        chain.doFilter(request, response);
    }

    private FirebaseToken decodeToken(String token) throws ResponseStatusException {
        FirebaseToken decodedToken;

        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
        } catch (FirebaseAuthException exception) {
            log.error(exception.getMessage());

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Can't verify token."
            );
        }

        if (decodedToken == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Token is invalid."
            );
        }

        return decodedToken;
    }
}
