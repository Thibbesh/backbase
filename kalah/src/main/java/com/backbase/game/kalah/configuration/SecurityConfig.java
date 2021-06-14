package com.backbase.game.kalah.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * REST Api must have secured way to access,
 * SecurityConfig, Will give basic
 * <p>authentication</p>
 * <p>authorization</p>
 * <p>Security threat</p>
 * <p>CSRF</p>
 * <p>XSS</p>
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

    private static final String[] AUTH_LIST = {
            "/v3/api-docs",
            "/v2/api-docs",
            "/swagger-ui.html",
            "/swagger-ui-custom.html"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_LIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        http.headers().frameOptions().disable();
    }

    /**
     * In memory Basic authentication. For now configure only 2 users
     * <p>backbase</p>
     * <p>user</p>
     * <p>USER</p>
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception
    {
        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser("backbase")
                .password(passwordEncoder().encode("backbase"))
                .roles("USER")
                .and()
                .withUser("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER");
    }

    /**
     * passwordEncoder is a bean to encode the password by BCryptPasswordEncoder.
     * @return BCrypt password.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
