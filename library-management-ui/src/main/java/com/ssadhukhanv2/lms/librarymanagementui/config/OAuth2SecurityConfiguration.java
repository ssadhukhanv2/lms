package com.ssadhukhanv2.lms.librarymanagementui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.endpoint.NimbusJwtClientAuthenticationParametersConverter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Subhrajit Sadhukhan
 */
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class OAuth2SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String ROLES_CLAIM = "roles";

    //Non secure endpoints for actuator and openapi
    private static final String[] unsecureEndPoints = {"/login/**", "/api-docs.yaml", "/swagger-ui/**", "/actuator/**"};

    /**
     * {@link WebSecurity}
     * <p>
     * General use of WebSecurity ignoring() method omits Spring Security and none of Spring Security’s features will be available. WebSecurity is based above HttpSecurity.
     *
     * @param
     * @throws Exception
     * @see <a href="https://stackoverflow.com/questions/56388865/spring-security-configuration-httpsecurity-vs-websecurity">when to use websecurity instead of httpsecurity in spring</a>
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(unsecureEndPoints);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http
                .cors().disable().authorizeRequests()
//                //.antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and().oauth2Login()
                .and().headers().frameOptions().disable()
                .and().headers().frameOptions().sameOrigin()
//                .and().formLogin().loginPage("/login").permitAll()
                .and().logout().invalidateHttpSession(true).clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/logout-success").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied")
                .and().csrf().disable();
    }

//    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
//        final OidcUserService oidcUserService = new OidcUserService();
//        return (userRequest) -> {
//            OidcUser oidcUser = oidcUserService.loadUser(userRequest);
//            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//            String accessTokenHash = oidcUser.getAccessTokenHash();
//            oidcUser = new DefaultOidcUser(grantedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
//            return oidcUser;
//
//        };
//    }
//
//
//    Collection<GrantedAuthority> generateAuthoritiesFromClaim(Collection<String> roles) {
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//                .collect(Collectors.toList());
//    }
}
