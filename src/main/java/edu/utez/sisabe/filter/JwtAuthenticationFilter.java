package edu.utez.sisabe.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.utez.sisabe.bean.UserDTO;
import edu.utez.sisabe.entity.User;
import edu.utez.sisabe.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final JwtTokenUtil jwtTokenUtil;

    Logger loggerMessage = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(String authenticationEndPoint, JwtTokenUtil jwtTokenUtil,
                                   AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.jwtTokenUtil = jwtTokenUtil;
        setFilterProcessesUrl(authenticationEndPoint);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserDTO credentials = new ObjectMapper().readValue(request.getInputStream(), UserDTO.class);
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(), credentials.getPassword()));
        } catch (IOException ignored) {
            return getAuthenticationManager().authenticate(null);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)  {
        String token = jwtTokenUtil.generateToken((User) authResult.getPrincipal());
        String bearer = "Bearer ";
        response.addHeader("Authorization", bearer + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.addHeader("Authorization", "Not authorized");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}