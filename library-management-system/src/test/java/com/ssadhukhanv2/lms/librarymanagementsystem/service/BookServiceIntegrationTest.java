package com.ssadhukhanv2.lms.librarymanagementsystem.service;

import com.ssadhukhanv2.lms.librarymanagementsystem.dao.BookDao;
import com.ssadhukhanv2.lms.librarymanagementsystem.exception.LibraryOperationException;
import com.ssadhukhanv2.lms.librarymanagementsystem.factory.BookDaoFactory;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
@DisplayName("Integration Test for BookService")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
class BookServiceIntegrationTest {

    private static final List<BookDao> bookDaoList = BookDaoFactory.createDefaultBookDaoList();
    private static List<String> isbnList = new ArrayList<>();
    private BookService bookService;
    private static Lorem lorem = LoremIpsum.getInstance();
    final String bookName = "Rich Dad Poor Dad";
    final String author = "Robert T. Kiyosaki";
    final String publisher = "Warner Books";
    private String isbn;

    @Autowired
    public BookServiceIntegrationTest(BookService bookService) {
        this.bookService = bookService;
    }


    @BeforeAll
    static void beforeAll() {
        log.info("Starting Integration Test for Book Service...");

    }

    @AfterAll
    static void afterAll() {

        log.info("Ending Integration Test for Book Service...");
    }

    @Test
    @DisplayName("Test to check book creation")
    @Order(1)
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
    @SneakyThrows
    void createBook() {
        Assumptions.assumeTrue(bookService != null && !bookDaoList.isEmpty());

        createDefaultBookSet();
        BookDao bookDao = BookDaoFactory.createBookDao(bookName
                , List.of(author),
                119.00,
                336,
                2000,
                publisher);
        isbn = bookDao.getIsbn();
        bookDao = bookService.createBook(bookDao);
        Assertions.assertNotNull(bookDao, "Book creation failed");
        Assertions.assertEquals(isbn, bookDao.getIsbn(), "Book creation failed");
        Assertions.assertEquals(bookName, bookDao.getName(), "Name of created book doesn't match");
        Assertions.assertEquals(author, bookDao.getAuthorList().get(0), "Author mismatch");

    }

    @Test
    @DisplayName("Testing to search all books")
    @Order(2)
    @SneakyThrows
    void getAllBooks() {
        final int numberOfBookInDB = isbnList.size() + 1;
        Assumptions.assumeTrue(bookService != null && !bookDaoList.isEmpty());
        Assertions.assertEquals(numberOfBookInDB, bookService.getAllBooks().size());
    }

    @Test
    @DisplayName("Test to search book by Isbn")
    @Order(3)
    @SneakyThrows
    void getBookByIsbn() {
        Assumptions.assumeTrue(bookService != null && !bookDaoList.isEmpty());
        BookDao bookDaoFound = bookService.getBookByIsbn(isbn);
        Assertions.assertNotNull(bookDaoFound, "Couldn't find book with Isbn: " + isbn);
        Assertions.assertEquals(isbn, bookDaoFound.getIsbn(), "Couldn't find book with Isbn: " + isbn);
    }


    @Test
    @DisplayName("Test Query By Example")
    @Order(4)
    @SneakyThrows
    void queryByExample() {
        Assumptions.assumeTrue(bookService != null && !bookDaoList.isEmpty());
        BookDao bookDao = new BookDao();
        bookDao.setName(bookName);
        List<BookDao> bookDaoList = bookService.queryByExample(bookDao);
        Assertions.assertEquals(1, bookDaoList.size(), "Query by Example not working");

        bookDao.setName(lorem.getWords(2, 5));
        Assertions.assertThrows(LibraryOperationException.class, () -> bookService.queryByExample(bookDao));
    }


    @Test
    @DisplayName("Test Query By Example with List")
    @Order(5)
    @SneakyThrows
    void queryByExampleWithList() {
        Assumptions.assumeTrue(bookService != null && !bookDaoList.isEmpty());
        BookDao bookDaoSearchWithAuthor = new BookDao();
        bookDaoSearchWithAuthor.setAuthorList(List.of("Thierry Templier"));
        List<BookDao> bookDaoSearchResultList = bookService.queryByExample(bookDaoSearchWithAuthor);
        Assertions.assertEquals(1, bookDaoSearchResultList.size());
    }

    @Test
    @DisplayName("Test to update book")
    @Order(6)
    void updateBook() throws LibraryOperationException {

        Assumptions.assumeTrue(bookService != null && !bookDaoList.isEmpty());

        BookDao bookDaoFound = bookService.getBookByIsbn(isbn);

        final String updatedBookName = "[UPDATED]" + bookDaoFound.getName();

        bookDaoFound.setName(updatedBookName);
        BookDao updatedBook = bookService.updateBook(bookDaoFound.getIsbn(), bookDaoFound);
        Assertions.assertEquals(updatedBookName, bookDaoFound.getName(), "Couldn't update book with Isbn: " + updatedBook.getIsbn());

    }

    @Test
    @DisplayName("Test to remove book")
    @Order(7)
    @SneakyThrows
    void removeBook() {
        Assumptions.assumeTrue(bookService != null && !bookDaoList.isEmpty());
        final int bookListSize = bookDaoList.size();
        bookService.removeBook(isbn);
        Assertions.assertEquals(bookListSize, bookService.getAllBooks().size(), "Couldn't remove books");
        //Negative Scenario
        Assertions.assertThrows(LibraryOperationException.class, () -> bookService.removeBook("RANDOMISBN"), "Negative Scenario failed");

    }

    @Test
    @DisplayName("Test Paged Book Catalogue")
    @Order(8)
    @SneakyThrows
    void getPagedBookCatalogue() {
        List<BookDao> bookDaoCatalogue = bookService.getPagedBookCatalogue(1, 50);
        Assertions.assertEquals(bookDaoList.size(), bookDaoCatalogue.size(), "Paged Book functionality not working");
        removeDefaultBookSet();
    }


    private void createDefaultBookSet() throws Exception {
        for (BookDao bookDao : bookDaoList) {
            isbnList.add(bookService.createBook(bookDao).getIsbn());
        }
    }

    private void removeDefaultBookSet() throws LibraryOperationException {
        for (String isbn : isbnList) {
            bookService.removeBook(isbn);
        }
    }
}