//package com.ssadhukhanv2.lms.librarymanagementsystem.service;
//
//import com.ssadhukhanv2.lms.librarymanagementsystem.dao.BookDao;
//import com.ssadhukhanv2.lms.librarymanagementsystem.entity.Book;
//import com.ssadhukhanv2.lms.librarymanagementsystem.factory.BookDaoFactory;
//import com.ssadhukhanv2.lms.librarymanagementsystem.mapper.BookToBookDaoMapper;
//import com.ssadhukhanv2.lms.librarymanagementsystem.repository.BookRepository;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Timeout;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Sort;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mock.*;
//import static org.mockito.Mockito.*;
//import static org.mockito.BDDMockito.*;
//import static org.assertj.core.api.Assert.*;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
//@Slf4j
//@Disabled
//class BookServiceUnitTest {
//
//
//    // By default, Mockito throws UnsupportedStubbingException
//    // when initialized mock is not called by least one of
//    // the test methods during execution
//    // lenient=true avoids this issue
//    @Mock(lenient = true)
//    private BookRepository bookRepository;
//
//    @InjectMocks
//    private BookService bookService;
//
//    private List<Book> bookList;
//
//    @Autowired
//    private BookToBookDaoMapper bookToBookDaoMapper;
//
//    @BeforeEach
//    void setUp() {
//        bookList = bookToBookDaoMapper.mapToBookList(BookDaoFactory.createDefaultBookDaoList());
//    }
//
//    @Test
//    @SneakyThrows
//    void createBook() {
//        //Given
//        Book book = bookList.get(0);
//        given(bookRepository.existsBookByIsbn(book.getIsbn()))
//                .willReturn(false);
//        given(bookRepository.save(Mockito.any(Book.class)))
//                .willReturn(book);
//
//        //When
//        BookDao bookDaoCreated = bookService.createBook(bookToBookDaoMapper.map(book));
//
//        //Then
//        assertThat(book.getTitle()).isEqualTo(bookDaoCreated.getName());
//        verify(bookRepository, atLeastOnce()).save(any(Book.class));
//        verify(bookRepository, never()).deleteBookByIsbn(any(String.class));
//        verify(bookRepository, never()).findAll(any(Example.class), any(Sort.class));
//        verify(bookRepository, never()).findBookByIsbn(any(String.class));
//    }
//
//    @Test
//    @SneakyThrows
//    void getAllBooks() {
//
//        //given
//        int bookListSize = bookList.size();
//        given(bookRepository.findAll(Mockito.any(Sort.class))).willReturn(bookList);
//
//        //when
//        int retrievedBookListSize = bookService.getAllBooks().size();
//
//        //Then
//        assertThat(bookListSize).isEqualTo(retrievedBookListSize);
//    }
//
//    @Test
//    @SneakyThrows
//    void getBookByIsbn() {
//
//        //given
//        Book book = bookList.get(0);
//        String bookTitle = book.getTitle();
//        given(bookRepository.findBookByIsbn(book.getIsbn())).willReturn(Optional.of(book));
//
//        //When
//        String retrievedBookName = bookService.getBookByIsbn(book.getIsbn()).getName();
//
//        //Then
//        assertThat(bookTitle).isEqualTo(retrievedBookName);
//    }
//
////    @Test
////    @SneakyThrows
////    void updateBook() {
////        Book book = bookList.get(0);
////        String isbn = book.getIsbn();
////        Mockito.when(bookRepository.findBookByIsbn(isbn)).thenReturn(Optional.of(book));
////        Mockito.when(bookRepository.save(book)).thenReturn(book);
////        Assertions.assertEquals(isbn, bookService.updateBook(isbn, bookToBookDaoMapper.map(book)).getIsbn());
////    }
////
////    @Test
////    void removeBook() {
////
////        Book book = bookList.get(0);
////        String isbn = book.getIsbn();
////        Mockito.doNothing().when(bookRepository.deleteBookByIsbn(isbn));
////        Mockito.when(bookRepository.existsBookByIsbn(isbn)).thenReturn(true);
////        Assertions. assert
////    }
////
////    @Test
////    void queryByExample() {
////        Book book = bookList.get(0);
////        Mockito.when(bookRepository.findAll(Example.of(book))).thenReturn(List.of(book));
////
////    }
////
////    @Test
////    void getPagedBookCatalogue() {
////    }
//}