//package com.ssadhukhanv2.lms.librarymanagementui.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//
////https://github.com/spring-projects/spring-security/issues/7526
////https://www.baeldung.com/java-config-spring-security
////https://www.baeldung.com/properties-with-spring
////@Configuration
////@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Value("${lms.user:lmsuser1}")
//    private String appUser;
//    @Value("${lms.user.password:l1m2s3p4a%s^s&w&o*r(d}")
//    private String appPassword;
//    @Value("${lms.user.role:LMS_USER}")
//    private String role;
//
//    private static final String[] openApiUrls = {"/v3/**", "/api-docs.yaml", "/swagger-ui/**"};
//
//
//    //https://stackoverflow.com/questions/56388865/spring-security-configuration-httpsecurity-vs-websecurity
//    //Warning encountered hence commented
//    //WARN 57244 --- [  restartedMain] o.s.s.c.a.web.builders.WebSecurity       : You are asking Spring Security to ignore Ant [pattern='/swagger-ui/**']. This is not recommended -- please use permitAll via HttpSecurity#authorizeHttpRequests instead.
////    @Override
////    public void configure(WebSecurity web) throws Exception {
////        web.ignoring()
////                .antMatchers(openApiUrls);
////    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests()
//                .antMatchers("/api/**").authenticated().anyRequest().permitAll()
//                .and()
//                .headers().frameOptions().disable()
//                .and()
//                .headers().frameOptions().sameOrigin()
//                .and()
//                .httpBasic()
//                .and()
//                .csrf().disable();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser(appUser)
//                .password(passwordEncoder().encode(appPassword))
//                .authorities(role);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
