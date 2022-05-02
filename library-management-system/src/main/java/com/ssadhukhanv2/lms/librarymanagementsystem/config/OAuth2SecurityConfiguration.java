package com.ssadhukhanv2.lms.librarymanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

/**
 * @author Subhrajit Sadhukhan
 */

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class OAuth2SecurityConfiguration extends WebSecurityConfigurerAdapter {


    //Non secure endpoints for actuator and openapi
    private static final String[] unsecureEndPoints = {"/v3/**", "/api-docs.yaml", "/swagger-ui/**", "/actuator/**"};

    /**
     * {@link WebSecurity}
     * <p>
     * General use of WebSecurity ignoring() method omits Spring Security and none of Spring Security’s features will be available. WebSecurity is based above HttpSecurity.
     *
     * @param web
     * @throws Exception
     * @see <a href="https://stackoverflow.com/questions/56388865/spring-security-configuration-httpsecurity-vs-websecurity">when to use websecurity instead of httpsecurity in spring</a>
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(unsecureEndPoints);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeHttpRequests()
                .antMatchers("/api/**")
                .hasAnyRole("ADMIN_USER", "BUSINESS_USER").anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(getJwtAuthenticationConverter());
        http.authorizeRequests().antMatchers("/token/**")
                //.hasAuthority("SCOPE_profile")
                .hasAnyRole("ADMIN_USER")
                .anyRequest().authenticated();

    }

    @Bean
    JwtAuthenticationConverter getJwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new JwtRoleToGrantedAuthorityConverter());
        return jwtAuthenticationConverter;
    }
}
