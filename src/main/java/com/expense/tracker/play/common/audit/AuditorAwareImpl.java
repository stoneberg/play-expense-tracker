package com.expense.tracker.play.common.audit;

import com.expense.tracker.play.config.security.service.CustomUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(user.getEmail());
    }

}
