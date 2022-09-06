package com.technokratos.security.filter;

import com.technokratos.security.userdetails.TokenAuthUserDetailsService;
import com.technokratos.util.AuthorizationHeaderUtil;
import com.technokratos.util.HttpResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthorizationFilter extends RequestHeaderAuthenticationFilter {

    private final TokenAuthUserDetailsService userDetailsService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {

        String authHeader = ((HttpServletRequest) request).getHeader(AUTHORIZATION);

        try {
            Optional<String> tokenCandidate = AuthorizationHeaderUtil.getTokenFromAuthorizationHeader(authHeader);
            if (tokenCandidate.isPresent()) {
                String token = tokenCandidate.get();

                PreAuthenticatedAuthenticationToken authenticationToken =
                        new PreAuthenticatedAuthenticationToken(userDetailsService.loadUserDetails(token), token);

                SecurityContext context = SecurityContextHolder.getContext();
                Authentication authentication = context.getAuthentication();

                if (Objects.isNull(authentication) || !authentication.getCredentials().equals(token)) {
                    context.setAuthentication(authenticationToken);
                }
            }
            chain.doFilter(request, response);
        } catch (Exception exception) {
            SecurityContextHolder.clearContext();
            HttpResponseUtil.putExceptionInResponse((HttpServletResponse) response,
                    exception, HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
