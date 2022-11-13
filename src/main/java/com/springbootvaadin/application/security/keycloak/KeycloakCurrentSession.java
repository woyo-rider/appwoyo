package com.springbootvaadin.application.security.keycloak;

import com.springbootvaadin.application.data.service.CurrentSession;
import com.springbootvaadin.application.data.service.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

/**
 * Implementation of {@link CurrentSession} that accesses the user info from the current
 * {@link OAuth2AuthenticationToken}.
 */
@Component
class KeycloakCurrentSession implements CurrentSession {
    Logger logger = LoggerFactory.getLogger(getClass());
    public Optional<UserInfo> getCurrentUser() {
        logger.info("===================== KeycloakCurrentSession implements CurrentSession.getCurrentUser() =================");
        return getOauth2User().map(user -> new UserInfo() {
            @Override
            public String getUsername() {
                return requireNonNullElse(user.getAttribute("preferred_username"), "");
            }

            @Override
            public String getFirstName() {
                return requireNonNullElse(user.getAttribute("given_name"), "");
            }

            @Override
            public String getLastName() {
                return requireNonNullElse(user.getAttribute("family_name"), "");
            }

            @Override
            public String getName() {
                return requireNonNull(user.getName());
            }
        });
    }

    private Optional<OAuth2User> getOauth2User() {
        logger.info("===================== KeycloakCurrentSession. Optional<OAuth2User> getOauth2User() =================");

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken) {
            return Optional.of(((OAuth2AuthenticationToken) authentication).getPrincipal());
        }
        return Optional.empty();
    }

    @Override
    public ZoneId getTimeZone() {
        return getOauth2User()
                .map(user -> user.<String>getAttribute("zoneinfo"))
                .map(ZoneId::of)
                .orElse(ZoneId.systemDefault());
    }

    @Override
    public boolean hasRole(String role) {
        logger.info("=====================KeycloakCurrentSession. hasRole(String role) =================");

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(role));
        }
        return false;
    }
}
