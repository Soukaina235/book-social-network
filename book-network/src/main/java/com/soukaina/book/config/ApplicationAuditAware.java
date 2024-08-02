package com.soukaina.book.config;

import com.soukaina.book.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<Integer> { // we are using Integer here because the user id is Integer
    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null ||
            !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken
        ) {
            return Optional.empty();
        }
        User userPrincipal = (User) authentication.getPrincipal();

        // ofNullable is for the case where userPrincipal is null, so that we just can return null
        return Optional.ofNullable(userPrincipal.getId());
    }
}
