package edu.utez.sisabe.config;

import edu.utez.sisabe.jwt.JwtTokenUtil;
import edu.utez.sisabe.filter.JwtAuthenticationEntryPoint;
import edu.utez.sisabe.filter.JwtAuthenticationFilter;
import edu.utez.sisabe.filter.JwtAuthorizationFilter;
import edu.utez.sisabe.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private JwtAuthorizationFilter jwtAuthorizationFilter;

    private final String rootAdrress;

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addExposedHeader("authorization");
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration((rootAdrress+"/**"), config);
        return new CorsFilter(source);
    }

    public WebSecurityConfig(JwtTokenUtil jwtTokenUtil, UserService userService,
                             JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, @Value("${rootAddress}") String rootAdrress){
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.rootAdrress = rootAdrress;
    }

    @PostConstruct
    public void init() throws Exception {
        this.jwtAuthenticationFilter = new JwtAuthenticationFilter((rootAdrress+"/login"), jwtTokenUtil,
                authenticationManager());
        this.jwtAuthorizationFilter = new JwtAuthorizationFilter(jwtTokenUtil, authenticationManager());
    }


    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors();//añade los permisos para cors
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers(HttpMethod.POST, (rootAdrress+"/login")).permitAll()
                .antMatchers((rootAdrress+"/admin/**")).hasAuthority("Administrador")
                .antMatchers((rootAdrress+"/alumno/**")).hasAuthority("Alumno")
                .antMatchers((rootAdrress+"/comite/**")).hasAuthority("Comité")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthorizationFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}