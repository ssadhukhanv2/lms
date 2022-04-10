package com.ssadhukhanv2.lms.librarymanagementsystem.service;


import com.ssadhukhanv2.lms.librarymanagementsystem.dao.BookDao;
import com.ssadhukhanv2.lms.librarymanagementsystem.entity.Book;

import com.ssadhukhanv2.lms.librarymanagementsystem.exception.LibraryOperationException;
import com.ssadhukhanv2.lms.librarymanagementsystem.mapper.BookToBookDaoMapper;

import com.ssadhukhanv2.lms.librarymanagementsystem.repository.BookRepository;
import com.ssadhukhanv2.lms.librarymanagementsystem.util.LibraryConstants;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookToBookDaoMapper bookToBookDaoMapper;

    //Create
    @Transactional(propagation = Propagation.REQUIRED)
    public BookDao createBook(final BookDao bookDao) throws LibraryOperationException {
        if (!bookRepository.existsBookByIsbn(bookDao.getIsbn())) {
            return bookToBookDaoMapper.map(bookRepository.save(bookToBookDaoMapper.map(bookDao)));
        }
        throw new LibraryOperationException("Book exists for: " + bookDao.getIsbn());

    }

    //Read all book, sorted by popularity(highest rated & highest voted)
    @Transactional(propagation = Propagation.REQUIRED)
    public List<BookDao> getAllBooks() throws LibraryOperationException {
        Sort defaultSort = Sort.by(Sort.Direction.DESC, "rating", "voters");
        Optional<List<BookDao>> bookDaoList = Optional.of(bookRepository.findAll(defaultSort)
                .stream().map(book -> bookToBookDaoMapper.map(book)).toList());
        return bookDaoList.filter(list -> !list.isEmpty()).orElseThrow(() -> new LibraryOperationException("No Books Found!!"));
    }

    //Read a specific book by isbn
    @Transactional(propagation = Propagation.REQUIRED)
    public BookDao getBookByIsbn(final String isbn) throws LibraryOperationException {
        return bookToBookDaoMapper.map(bookRepository
                .findBookByIsbn(isbn)
                .orElseThrow(() -> new LibraryOperationException("Book Not Found for ISBN: " + isbn)));
    }

    //Update
    @Transactional(propagation = Propagation.REQUIRED)
    public BookDao updateBook(final String isbn, final BookDao bookDao) throws LibraryOperationException {
        if (bookDao.getIsbn() == null || bookDao.getIsbn().isBlank()) {
            bookDao.setIsbn(isbn);
        }
        Book bookInDB = bookRepository.findBookByIsbn(isbn).orElseThrow(() -> new LibraryOperationException("No book available to update for ISBN: " + isbn));

        Book bookToSave = bookToBookDaoMapper.map(bookDao);
        bookToSave.setId(bookInDB.getId());
        return bookToBookDaoMapper.map(bookRepository.save(bookToSave));
    }


    //Delete
    @Transactional(propagation = Propagation.REQUIRED)
    public BookDao removeBook(final String isbn) throws LibraryOperationException {

        if (bookRepository.existsBookByIsbn(isbn))
            return bookToBookDaoMapper
                    .map(bookRepository.deleteBookByIsbn(isbn)
                            .orElseThrow(() -> new LibraryOperationException("Deletion failed for ISBN: " + isbn)));

        throw new LibraryOperationException("No books available to delete for ISBN: " + isbn);

    }

    //Query By Example
    @Transactional(propagation = Propagation.REQUIRED)
    public List<BookDao> queryByExample(final BookDao bookDaoExampleObject) throws LibraryOperationException {
        Optional<List<BookDao>> bookDaoList =
                Optional.of(bookToBookDaoMapper
                        .map(bookRepository
                                .findAll(Example.of(bookToBookDaoMapper.map(bookDaoExampleObject),
                                        ExampleMatcher.matchingAny()
                                                .withIgnoreCase()))));
        return bookDaoList.filter((list) -> !list.isEmpty()).orElseThrow(() -> new LibraryOperationException("No book match search criteria"));

    }

    //Paged Results
    @Transactional(propagation = Propagation.REQUIRED)
    public List<BookDao> getPagedBookCatalogue(int pageNumber, int pageSize) throws LibraryOperationException {
        Sort sort = Sort.by(Sort.Direction.DESC, "rating", "voters");
        //pageNumber-1 is added as index starts at 0
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        Page<Book> bookPages = bookRepository.findAll(pageable);
        int totalPagesInDB = bookPages.getTotalPages();
        int numberOfElements = bookPages.getNumberOfElements();
        return bookToBookDaoMapper
                .map(Optional.of(bookPages.getContent())
                        .filter(bookList -> !bookList.isEmpty()).orElseThrow(() -> new LibraryOperationException("No books founds at page number: " + pageNumber + " for page size:" + pageSize)));
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<BookDao> getBookByGenres(List<String> genres) throws LibraryOperationException {
        return bookToBookDaoMapper.map(bookRepository.findBookByGenres(genres).orElseThrow(() -> new LibraryOperationException("No Book found for genres" + genres)));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<BookDao> getBookByAuthors(List<String> authorList) throws LibraryOperationException {
        return bookToBookDaoMapper.map(bookRepository.findBookByAuthorList(authorList).orElseThrow(() -> new LibraryOperationException("No Book found for authors" + authorList)));
    }

    private Sort.Direction getSortDirectionFromString(String sortDirection) throws LibraryOperationException {
        if (sortDirection.equalsIgnoreCase("desc")) {
            return Sort.Direction.DESC;
        } else if (sortDirection.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        } else
            throw new LibraryOperationException("Sort not supported with Sort Direction" + sortDirection);
    }

}
