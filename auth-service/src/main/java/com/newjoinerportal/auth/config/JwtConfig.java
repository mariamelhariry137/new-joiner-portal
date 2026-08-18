package com.newjoinerportal.auth.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class JwtConfig {

    @Bean
    public JwtEncoder jwtEncoder(
            @Value("${jwt.secret}") String encodedSecret) {

        byte[] secretBytes =
                Base64.getDecoder().decode(encodedSecret);

        if (secretBytes.length < 32) {
            throw new IllegalStateException(
                    "JWT_SECRET must contain at least 32 bytes"
            );
        }

        SecretKey secretKey =
                new SecretKeySpec(
                        secretBytes,
                        "HmacSHA256"
                );

        JWKSource<SecurityContext> jwkSource =
                new ImmutableSecret<>(secretKey);

        return new NimbusJwtEncoder(jwkSource);
    }
}