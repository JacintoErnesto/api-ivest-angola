package ucan.edu.api_sig_invest_angola.dtos.auth;

import java.time.LocalDateTime;

public record AuthContaReturnDTO(
        Long id,
        String username,
        String tipoConta,
        boolean perfilCompleto,
        LocalDateTime dataRegisto,
        java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> authorities,
        boolean accountNonExpired,
        boolean accountNonLocked,
        boolean credentialsNonExpired,
        boolean enabled
) {}
