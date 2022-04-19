package com.ssadhukhanv2.lms.librarymanagementui.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssadhukhanv2.lms.librarymanagementui.config.LibraryManagementSystemClient;
import com.ssadhukhanv2.lms.librarymanagementui.client.domain.BookDao;

import com.ssadhukhanv2.lms.librarymanagementui.domain.BookDetails;
import com.ssadhukhanv2.lms.librarymanagementui.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryManagementSystemService {
    @Autowired
    LibraryManagementSystemClient libraryManagementSystemClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BookMapper bookMapper;

    public List<BookDetails> getAllbook() {
//        List<BookDetails> bookDetailsList = (List<BookDetails>) objectMapper.convertValue(libraryManagementSystemClient
//                        .getLibraryBookCatalogue(null, null).getBody(), List.class)
//                .stream().map(book -> bookMapper.map(objectMapper.convertValue(book, BookDao.class)))
//                .collect(Collectors.toList());
        return convertToBookDetailsList((List<LinkedHashMap>) libraryManagementSystemClient.getLibraryBookCatalogue(null, null).getBody());
    }

    public BookDetails findBookByIsbn(final String isbn) {
        BookDao bookDao = objectMapper.convertValue(libraryManagementSystemClient.getBookByIsbn(isbn).getBody(), BookDao.class);
        return bookMapper.map(bookDao);
    }

    public List<BookDetails> searchBooksByAuthors(final List<String> authorList) {
        BookDao bookDao = new BookDao();
        bookDao.setAuthorList(authorList);
        List<BookDetails> bookDetailsList = convertToBookDetailsList((List<LinkedHashMap>) libraryManagementSystemClient.searchAuthor(bookDao).getBody());
        return bookDetailsList;
    }

    private List<BookDetails> convertToBookDetailsList(List<LinkedHashMap> linkedHashMaps) {
        return linkedHashMaps.stream().map(bookDao -> bookMapper.map(objectMapper.convertValue(bookDao, BookDao.class)))
                .collect(Collectors.toList());
    }

}
