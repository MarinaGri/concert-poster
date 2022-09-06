package com.technokratos.security.userdetails;

import com.technokratos.dto.enums.Role;
import com.technokratos.exception.token.AuthenticationHeaderException;
import com.technokratos.model.UserEntity;
import com.technokratos.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.technokratos.consts.SecurityConst.ROLE_PREFIX;

@RequiredArgsConstructor
@Service
public class TokenAuthUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private final JwtTokenService jwtTokenService;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        return loadUserDetails((String) token.getCredentials());
    }

    public UserDetails loadUserDetails(String token) {
        try {
            UserEntity user = Optional.ofNullable(jwtTokenService.getUserByToken(token))
                    .orElseThrow(() -> new UsernameNotFoundException("Unknown user by token " + token));

            List<SimpleGrantedAuthority> authorities = getAuthorities(user.getRole());

            return AccountUserDetails.builder()
                    .id(user.getId())
                    .username(user.getEmail())
                    .authorities(authorities)
                    .isAccountNonExpired(true)
                    .isCredentialsNonExpired(true)
                    .isAccountNonLocked(true)
                    .isEnabled(true)
                    .build();
        } catch (Exception exception) {
            throw new AuthenticationHeaderException(exception.getMessage());
        }
    }

    private List<SimpleGrantedAuthority> getAuthorities(Role role) {
        return Collections.singletonList(
                new SimpleGrantedAuthority(ROLE_PREFIX + role));
    }
}
