package com.ssadhukhanv2.lms.librarymanagementui.controller;


import com.ssadhukhanv2.lms.librarymanagementui.domain.BookDetails;
import com.ssadhukhanv2.lms.librarymanagementui.service.LibraryManagementSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
public class LibraryManagementUiController {


    @Autowired
    LibraryManagementSystemService libraryManagementSystemService;

    @GetMapping(value = "/login")
    public String getLoginPage(Model model) {
        return "login";
    }

    @GetMapping(value = "/addBook")
    public String getAddBookPage(Model model) {
        return "addBook";
    }

    @GetMapping(value = "/addUser")
    public String getAddUserPage(Model model) {return "addUser";}

    @GetMapping(value = {"", "/", "/dashboard"})
    public String getDashboardPage(Model model, @RequestParam(value = "author", required = false) final String authorName) {
        Optional<List<BookDetails>> bookDetailsListOptional = Optional.empty();
        if (authorName != null) {
            bookDetailsListOptional = Optional.of(libraryManagementSystemService.searchBooksByAuthors(List.of(authorName)));
        } else {
            bookDetailsListOptional = Optional.of(libraryManagementSystemService.getAllbook());
        }
        model.addAttribute("bookDetailsList", bookDetailsListOptional.get());
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
}
