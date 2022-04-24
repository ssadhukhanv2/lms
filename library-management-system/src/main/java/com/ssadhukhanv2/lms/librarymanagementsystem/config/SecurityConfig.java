package com.ssadhukhanv2.lms.librarymanagementsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Basic Security Configuration for the Library Management System.<br/>
 *
 * App Credentials may be externalized using:<
 * <ul>
 *     <li>lms.user:lmsuser1</li>
 *     <li>lms.user.password:l1m2s3p4a%s^s&w&o*r(d</li>
 *     <li>lms.user.role:LMS_USER</li>
 * </ul>
 *
 * @see <a href="https://github.com/spring-projects/spring-security/issues/7526">using the configure(AuthenticationManagerBuilder auth) instead of configureGlobal method </a>
 * @see <a href="https://www.baeldung.com/java-config-spring-security">baeldung spring security</a>
 * @see <a href="https://www.baeldung.com/properties-with-spring"> baeldung using properties in spring</a>
 *
 * @author Subhrajit Sadhukhan
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${lms.user:lmsuser1}")
    private String appUser;
    @Value("${lms.user.password:l1m2s3p4a%s^s&w&o*r(d}")
    private String appPassword;
    @Value("${lms.user.role:LMS_USER}")
    private String role;

    private static final String[] openApiUrls = {"/v3/**", "/api-docs.yaml", "/swagger-ui/**"};


    /**
     * {@link WebSecurity}
     *
     * General use of WebSecurity ignoring() method omits Spring Security and none of Spring Security’s features will be available. WebSecurity is based above HttpSecurity.
     * @param web
     * @throws Exception
     *
     * @see <a href="https://stackoverflow.com/questions/56388865/spring-security-configuration-httpsecurity-vs-websecurity">when to use websecurity instead of httpsecurity in spring</a>
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(openApiUrls);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/api/**").authenticated().anyRequest().permitAll()
                .and()
                .csrf()
                .disable();
        http.httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(appUser)
                .password(passwordEncoder().encode(appPassword))
                .authorities(role);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
