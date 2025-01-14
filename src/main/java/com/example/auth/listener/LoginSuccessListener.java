package com.example.auth.listener;

import com.example.auth.kafka.ActionRecordSender;
import com.example.avro.ActionRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
    //todo: Сделать фиксацию пользовательского входа через расширение Keycloak, добавить обработку неудачной аутентификации

    private final ActionRecordSender sender;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {

        Jwt token = (Jwt) event.getAuthentication().getPrincipal();
        Map<String, Object> claims = token.getClaims();

        Instant loginTime = Instant.now();

        String username = claims.get("preferred_username").toString();
        ActionRecord message = ActionRecord.newBuilder()
                .setUsername(username)
                .setAction("LOGIN")
                .setActionResult("SUCCESS")
                .setActionDate(Instant.now().toString())
                .build();

        sender.sendMessage(message);

        log.debug("User {} logged in at {}", username, loginTime);
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
