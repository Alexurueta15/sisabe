package edu.utez.sisabe.filter;

import edu.utez.sisabe.entity.Logbook;
import edu.utez.sisabe.jwt.JwtTokenUtil;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenUtil jwtTokenUtil;

    Logger loggerMessage = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    public JwtAuthorizationFilter(JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String requestTokenHeader = request.getHeader("Authorization");

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
            loggerMessage.info("Bearer confirmado");
            UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else {
            loggerMessage.warn("JWT Token does not begin with Bearer String");
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
            throws IllegalArgumentException, JwtException {
        String token = request.getHeader("Authorization").substring(6);
        String user = jwtTokenUtil.getUsernameFromToken(token);
        if (user != null) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null,
                            Collections.singletonList(new SimpleGrantedAuthority(jwtTokenUtil.getRoleFromToken(token))));
            if (!request.getMethod().equals("GET")) {

                authenticationToken.setDetails(new Logbook(user, request.getRequestURI(),
                        request.getRemoteAddr(), request.getMethod()));
            }
            return authenticationToken;
        }
        loggerMessage.warn("Token inválido");
        return null;
    }
}