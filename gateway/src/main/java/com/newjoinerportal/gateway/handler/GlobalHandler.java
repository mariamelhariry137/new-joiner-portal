package com.newjoinerportal.gateway.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
@Order(-1)
public class GlobalHandler implements ErrorWebExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalHandler.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        HttpStatus status;
        String message;

        // Handle different gateway-specific exceptions
        if (ex instanceof NotFoundException) {
            status = HttpStatus.NOT_FOUND;
            message = "Service not found: " + ex.getMessage();
        } else if (ex instanceof ResponseStatusException) {
            ResponseStatusException rse = (ResponseStatusException) ex;
            status = (HttpStatus) rse.getStatusCode();
            message = rse.getReason();
        } else if (ex.getCause() instanceof java.net.ConnectException) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
            message = "Service is currently unavailable. Please try again later.";
            logger.error("Connection error: {}", ex.getCause().getMessage());
        } else if (ex instanceof java.util.concurrent.TimeoutException) {
            status = HttpStatus.REQUEST_TIMEOUT;
            message = "Service request timed out. Please try again.";
            logger.error("Timeout error: {}", ex.getMessage());
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "An unexpected error occurred in the gateway.";
            logger.error("Unexpected gateway error: {}", ex.getMessage(), ex);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", System.currentTimeMillis());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);
        response.put("path", exchange.getRequest().getURI().getPath());

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(response);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

            exchange.getResponse().setStatusCode(status);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (Exception e) {
            logger.error("Error writing error response: {}", e.getMessage());
            return Mono.error(e);
        }
    }
}