package com.ssadhukhanv2.lms.librarymanagementui.config;

import com.ssadhukhanv2.lms.librarymanagementui.client.LibraryManagementSystemClientConfig;
import com.ssadhukhanv2.lms.librarymanagementui.client.domain.BookDao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@FeignClient(value = "library", url = "http://localhost:8080/api/v1/book", configuration = LibraryManagementSystemClientConfig.class)
@FeignClient(value = "library-management-system/api/v1/book", configuration = LibraryManagementSystemClientConfig.class)
public interface LibraryManagementSystemClient {
    //Create
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<?> addBook(@RequestBody final BookDao bookDao);

    //Read
    @GetMapping(value = "/{isbn}")
    public ResponseEntity<?> getBookByIsbn(@PathVariable(value = "isbn") final String isbn);

    //Update
    @PutMapping(value = "/{isbn}")
    public ResponseEntity<?> updateBook(@PathVariable(value = "isbn") final String isbn, @RequestBody final BookDao bookDao);

    //Delete
    @DeleteMapping(value = "/{isbn}")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "isbn") final String isbn);

    @PostMapping(value = "/search")
    public ResponseEntity<?> searchBooks(@RequestBody BookDao bookDao);


    @PostMapping(value = "/search/authors")
    public ResponseEntity<?> searchAuthor(@RequestBody BookDao bookDao);

    @PostMapping(value = "/search/genres")
    public ResponseEntity<?> searchGenres(@RequestBody BookDao bookDao);


    @GetMapping(value = "")
    public ResponseEntity<?> getLibraryBookCatalogue(@RequestParam(value = "pageNumber", required = false) final Integer pageNumber,
                                                     @RequestParam(value = "pageSize", required = false) final Integer pageSize);

}
