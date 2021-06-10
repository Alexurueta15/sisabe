package edu.utez.sisabe.config;

import edu.utez.sisabe.jwt.JwtTokenUtil;
import edu.utez.sisabe.filter.JwtAuthenticationEntryPoint;
import edu.utez.sisabe.filter.JwtAuthenticationFilter;
import edu.utez.sisabe.filter.JwtAuthorizationFilter;
import edu.utez.sisabe.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.PostConstruct;

@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private JwtAuthorizationFilter jwtAuthorizationFilter;


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addExposedHeader("authorization");
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    public WebSecurityConfig(JwtTokenUtil jwtTokenUtil, UserService userService,
                             JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                             BCryptPasswordEncoder bCryptPasswordEncoder){
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    public void init() throws Exception {
        this.jwtAuthenticationFilter = new JwtAuthenticationFilter("/login", jwtTokenUtil,
                authenticationManager());
        this.jwtAuthorizationFilter = new JwtAuthorizationFilter(jwtTokenUtil, authenticationManager());
    }


    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors();//añade los permisos para cors
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/admin/**").hasAuthority("Administrador")
                .antMatchers("/alumno/**").hasAuthority("Alumno")
                .antMatchers("/comite/**").hasAuthority("Comité")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAfter(jwtAuthorizationFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}