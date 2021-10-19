package com.andreiev.readinglist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Profile("production")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ReaderRepository readerRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/").access("hasRole('READER')") // require READER access
                    .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")  // set login form path
                    .failureUrl("/login?error=true")
                .loginProcessingUrl("login")
                .and()
                .csrf().disable()
                .logout()
                    .permitAll()
                .and().headers().frameOptions().sameOrigin() // for correct h2-console displaying
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // define custom UserDetailsService
        auth
                .userDetailsService(username -> readerRepository.findById(username).orElse(null));
    }
}
