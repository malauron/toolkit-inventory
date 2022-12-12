package com.toolkit.inventory.Security.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Created by jt on 6/13/20.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().cors().and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and().authorizeRequests().antMatchers("/api/v1/login/**").permitAll()
                .anyRequest().authenticated();
//                .and()
//                .exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .and()
//                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        http.sessionManagement().sessionCreationPolicy(STATELESS)
//                .and().authorizeRequests().anyRequest().permitAll();

//                http
//                .authorizeRequests(authorize -> {
//                    authorize
//                            .antMatchers("/h2-console/**").permitAll() //do not use in production!
//                            .antMatchers(HttpMethod.GET,"/api/v1/**").permitAll()
//                            .antMatchers(HttpMethod.GET, "/api/v1/beer/**")
//                                .hasAnyRole("ADMIN", "CUSTOMER", "USER")
//                            .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}")
//                                .hasAnyRole("ADMIN", "CUSTOMER", "USER")
//                            .mvcMatchers("/brewery/breweries")
//                                .hasAnyRole("ADMIN", "CUSTOMER")
//                            .mvcMatchers(HttpMethod.GET, "/brewery/api/v1/breweries")
//                                .hasAnyRole("ADMIN", "CUSTOMER")
//                            .mvcMatchers("/beers/find", "/beers/{beerId}")
//                                .hasAnyRole("ADMIN", "CUSTOMER", "USER");
//                } )
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().and()
//                .httpBasic()
//                .and().csrf().disable();

                //h2 console config
//                http.headers().frameOptions().sameOrigin();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }













}
