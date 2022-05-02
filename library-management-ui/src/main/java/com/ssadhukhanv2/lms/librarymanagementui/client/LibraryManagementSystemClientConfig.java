package com.ssadhukhanv2.lms.librarymanagementui.client;

import feign.RequestInterceptor;

import feign.RequestTemplate;
import feign.auth.BasicAuthRequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;


/**
 * @see <a href="https://stackoverflow.com/questions/21920268/basic-authentication-for-rest-api-using-spring-resttemplate"></a>
 * <p>
 * https://www.baeldung.com/netflix-feign-vs-openfeign
 * https://www.baeldung.com/spring-cloud-openfeign
 * https://www.baeldung.com/guide-to-okhttp
 * https://square.github.io/okhttp/
 */

@Slf4j
@Configuration
public class LibraryManagementSystemClientConfig {
    @Value("${lms.app.user}")
    private String appUser;
    @Value("${lms.app.user.password:l1m2s3p4a%s^s&w&o*r(d}")
    private String appPassword;



    private static final String CLIENT_REGISTRATION_ID = "lmsui";

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient.Builder()
                //Set connection timeout
                .connectTimeout(10, TimeUnit.SECONDS)
                //Set the read timeout
                .readTimeout(10, TimeUnit.SECONDS)
                //Set write timeout
                .writeTimeout(10, TimeUnit.SECONDS)
                //Whether to automatically reconnect
                .retryOnConnectionFailure(true)
                .connectionPool(new ConnectionPool(10, 5L, TimeUnit.MINUTES))
                .build();
    }

//    @Bean
//    public RequestInterceptor requestInterceptor() {
////        return requestTemplate -> {
//
//
//
//            //RequestTemplate requestTemplate = new RequestTemplate();
//            // RequestTemplate has the below method which allows
//            // void apply(RequestTemplate var1);
//            //so we modify requestTemplate here to send a set of headers(including security information) with the request
////            String auth = appUser + ":" + appPassword;
////            byte[] encodedAuth = Base64Utils.encode(auth.getBytes(StandardCharsets.UTF_8));
////            String encodedAuthString = new String(encodedAuth);
////            String authHeader = "Basic " + encodedAuthString;
////            //requestTemplate.header("Authorization", "Basic bG1zdXNlcjE6bDFtMnMzcDRhJXNecyZ3Jm8qcihk");
////            requestTemplate.header("Authorization", authHeader);
////            requestTemplate.header("password", appPassword);
////            requestTemplate.header("Accept", ContentType.APPLICATION_JSON.getMimeType());
////            return requestTemplate;
////        };
//
//        return new BasicAuthRequestInterceptor(appUser, appPassword);
//    }
}
