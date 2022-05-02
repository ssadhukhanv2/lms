package com.ssadhukhanv2.lms.librarymanagementui.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssadhukhanv2.lms.librarymanagementui.client.domain.BookDao;
import com.ssadhukhanv2.lms.librarymanagementui.domain.BookDetails;
import com.ssadhukhanv2.lms.librarymanagementui.mapper.BookMapper;
import com.ssadhukhanv2.lms.librarymanagementui.service.LibraryManagementSystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("")
@Slf4j
public class LibraryManagementUiController {

    @Autowired
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
    @Autowired
    LibraryManagementSystemService libraryManagementSystemService;

    @Autowired
    RestTemplate restTemplate;


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BookMapper bookMapper;

    @GetMapping(value = "/login")
    public String getLoginPage(Model model) {
        return "login";
    }

    @GetMapping(value = "/addBook")
//    @PreAuthorize("hasRole('ROLE_BUSINESS_USER')")
    @PreAuthorize("hasRole('ROLE_BUSINESS_USER')")
    public String getAddBookPage(Model model) {
        return "addBook";
    }

    @GetMapping(value = "/addUser")
    @PreAuthorize("hasRole('ROLE_ADMIN_USER')")
    public String getAddUserPage(Model model) {
        return "addUser";
    }

    @GetMapping(value = "/access-denied")
    public String getAccessDeniedPage() {
        return "redirect:/?accessDenied";
    }

    @GetMapping(value = {"", "/", "/dashboard"})
    public String getDashboardPage(Model model, @AuthenticationPrincipal OidcUser principal, @RequestParam(value = "author", required = false) final String authorName) {
        String jwtAccessToken = getAuthToken(principal);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtAccessToken);

        String url = "http://library-management-system/api/v1/book";
        HttpEntity<List<BookDetails>> entity = new HttpEntity<>(headers);
        ResponseEntity<List<BookDetails>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<BookDetails>>() {
        });

        List<BookDetails> bookDetailsList = responseEntity.getBody();

//        Optional<List<BookDetails>> bookDetailsListOptional = Optional.empty();
//        if (authorName != null) {
//            bookDetailsListOptional = Optional.of(libraryManagementSystemService.searchBooksByAuthors(List.of(authorName)));
//        } else {
//            bookDetailsListOptional = Optional.of(libraryManagementSystemService.getAllbook());
//        }
//        return convertToBookDetailsList((List<LinkedHashMap>) libraryManagementSystemClient.getLibraryBookCatalogue(null, null).getBody());
//        model.addAttribute("bookDetailsList", bookDetailsListOptional.get());
        model.addAttribute("bookDetailsList", bookDetailsList);
        return "bookDashboard";
    }

    @GetMapping(value = {"/preview", "/preview/{isbn}"})
    public String getPreviewBookPage(Model model, @PathVariable(name = "isbn", required = false) String isbn) {
        if (null != isbn) {
            BookDetails bookDetails = libraryManagementSystemService.findBookByIsbn(isbn);
            model.addAttribute("bookDetails", bookDetails);
        }
        return "previewBook";
    }

    @GetMapping(value = "/edit")
    public String getEditBookPage(Model model) {
        return "edit";
    }

    @GetMapping(value = "/logout-success")
    public String getLogoutPage(Model model) {
        return "redirect:/login?logout";
    }

    private String getAuthToken(OidcUser principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient oAuth2AuthorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(), oAuth2AuthenticationToken.getName());


        String jwtAccessToken = oAuth2AuthorizedClient.getAccessToken().getTokenValue();
        log.info("jwtAccessToken: " + jwtAccessToken);
        log.info("Principal: " + principal);

        OidcIdToken oidcIdToken = principal.getIdToken();
        String idTokenValue = oidcIdToken.getTokenValue();
        log.info("idTokenValue: " + idTokenValue);

        log.info("Authorization | Bearer {}", jwtAccessToken);
        return jwtAccessToken;

    }

    private List<BookDetails> convertToBookDetailsList(List<LinkedHashMap> linkedHashMaps) {
        return linkedHashMaps.stream().map(bookDao -> bookMapper.map(objectMapper.convertValue(bookDao, BookDao.class)))
                .collect(Collectors.toList());
    }
}
