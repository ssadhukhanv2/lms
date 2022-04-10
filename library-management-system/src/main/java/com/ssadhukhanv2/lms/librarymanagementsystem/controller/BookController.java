package com.ssadhukhanv2.lms.librarymanagementsystem.controller;

import com.ssadhukhanv2.lms.librarymanagementsystem.dao.BookDao;
import com.ssadhukhanv2.lms.librarymanagementsystem.exception.LibraryOperationException;
import com.ssadhukhanv2.lms.librarymanagementsystem.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/book")
public class BookController {

    @Autowired
    BookService bookService;


    //Create
    @PostMapping(value = "")
    public ResponseEntity<?> addBook(@RequestBody final BookDao bookDao) {
        try {
            final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("//" + bookDao.getIsbn()).build().toUri();
            return ResponseEntity.created(uri).body(bookService.createBook(bookDao));
        } catch (LibraryOperationException libraryOperationException) {
            return new ResponseEntity<>(libraryOperationException.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    //Read
    @GetMapping(value = "/{isbn}")
    public ResponseEntity<?> getBookByIsbn(@PathVariable(value = "isbn") final String isbn) {
        try {
            BookDao bookDao = bookService.getBookByIsbn(isbn);
            return new ResponseEntity<>(bookDao, HttpStatus.OK);
        } catch (LibraryOperationException libraryOperationException) {
            return new ResponseEntity<>(libraryOperationException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    //Update
    @PutMapping(value = "/{isbn}")
    public ResponseEntity<?> updateBook(@PathVariable(value = "isbn") final String isbn, @RequestBody final BookDao bookDao) {
        try {
            return ResponseEntity.ok(bookService.updateBook(isbn, bookDao));
        } catch (LibraryOperationException libraryOperationException) {
            return new ResponseEntity<>(libraryOperationException.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    //Delete
    @DeleteMapping(value = "/{isbn}")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "isbn") final String isbn) {
        try {
            bookService.removeBook(isbn);
            return new ResponseEntity<>(isbn, HttpStatus.ACCEPTED);
        } catch (LibraryOperationException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @PostMapping(value = "/search")
    public ResponseEntity<?> searchBooks(@RequestBody BookDao bookDao) {
        try {
            return new ResponseEntity<>(bookService.queryByExample(bookDao), HttpStatus.OK);
        } catch (LibraryOperationException libraryOperationException) {
            return new ResponseEntity<>(libraryOperationException.getMessage(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @PostMapping(value = "/search/authors")
    public ResponseEntity<?> searchAuthor(@RequestBody BookDao bookDao) {
        try {
            return new ResponseEntity<>(bookService.getBookByAuthors(bookDao.getAuthorList()), HttpStatus.OK);
        } catch (LibraryOperationException libraryOperationException) {
            return new ResponseEntity<>(libraryOperationException.getMessage(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(value = "/search/genres")
    public ResponseEntity<?> searchGenres(@RequestBody BookDao bookDao) {
        try {
            return new ResponseEntity<>(bookService.getBookByGenres(bookDao.getGenres()), HttpStatus.OK);
        } catch (LibraryOperationException libraryOperationException) {
            return new ResponseEntity<>(libraryOperationException.getMessage(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Operation(summary = "List all the books in the library")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all books in the library",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "204", description = "No books available in the library with the given search criteria", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error while fetching book.")
    })
    @GetMapping(value = "")
    public ResponseEntity<?> getLibraryBookCatalogue(@RequestParam(value = "pageNumber", required = false) final Integer pageNumber,
                                                     @RequestParam(value = "pageSize", required = false) final Integer pageSize) {
        try {
            if (pageNumber == null || pageSize == null) {
                return getAllBooks();
            }
            List<BookDao> bookDaoList = bookService.getPagedBookCatalogue(pageNumber, pageSize);
            return ResponseEntity.ok(bookDaoList);
        } catch (LibraryOperationException libraryOperationException) {
            return new ResponseEntity<>(libraryOperationException.getMessage(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // Response Codes selected based on below logic
    // https://stackoverflow.com/questions/11746894/what-is-the-proper-rest-response-code-for-a-valid-request-but-an-empty-data
    //@GetMapping("/")
    private ResponseEntity<?> getAllBooks() {
        try {
            return ResponseEntity.ok(bookService.getAllBooks());
        } catch (LibraryOperationException libraryOperationException) {
            return new ResponseEntity<>(libraryOperationException.getMessage(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
