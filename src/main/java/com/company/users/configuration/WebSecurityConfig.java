package com.company.users.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   /* @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable();
                //.authorizeRequests()
                //.anyRequest().authenticated();
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().disable().
                authorizeRequests()
                .antMatchers(HttpMethod.PUT,"/users/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/users/").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/users/{id}").hasRole("USER")
                .and().httpBasic();

    }

    // In-memory authentication to authenticate the user i.e. the user credentials are stored in the memory.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("guest").password("{noop}guest1234").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin1234").roles("ADMIN");
    }

}
