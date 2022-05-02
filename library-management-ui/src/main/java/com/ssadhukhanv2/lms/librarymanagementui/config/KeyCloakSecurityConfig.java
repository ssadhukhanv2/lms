//package com.ssadhukhanv2.lms.librarymanagementui.config;
//
//import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
//import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
//import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
//import org.springframework.security.core.session.SessionRegistryImpl;
//import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
//import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
///**
// * @author Subhrajit Sadhukhan
// */
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@KeycloakConfiguration
//public class KeyCloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder){
//        SimpleAuthorityMapper grantedAuthorityMapper = new SimpleAuthorityMapper();
//        grantedAuthorityMapper.setPrefix("ROLE_");
//        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
//        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(grantedAuthorityMapper);
//        authenticationManagerBuilder.authenticationProvider(keycloakAuthenticationProvider);
//    }
//    @Override
//    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
//        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
//    }
//
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        super.configure(httpSecurity);
////        httpSecurity.authorizeHttpRequests()
//////                .antMatchers("/api/**").authenticated()
//////                .anyRequest().permitAll()
//////                .and().headers().frameOptions().disable()
//////                .and().headers().frameOptions().sameOrigin()
//////                .and().httpBasic().and().csrf().disable();
//        httpSecurity.authorizeRequests()
//                //.antMatchers("/login").permitAll()
//                .anyRequest().authenticated()
//                .and().headers().frameOptions().disable()
//                .and().headers().frameOptions().sameOrigin()
////                .and().formLogin().loginPage("/login").permitAll()
//                .and().logout().invalidateHttpSession(true).clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/logout-success").permitAll()
//                .and()
//                .exceptionHandling().accessDeniedPage("/access-denied")
//                .and().csrf().disable();
//
//    }
//
//
//}
