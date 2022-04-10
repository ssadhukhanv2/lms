package com.ssadhukhanv2.lms.librarymanagementsystem.repository;

import com.ssadhukhanv2.lms.librarymanagementsystem.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, BigInteger> {
    public Optional<Book> findBookByIsbn(String book);


    @Query("{genres:{$all:?0}}")
    public Optional<List<Book>> findBookByGenres(List<String> genres);


    @Query("{authors:{$all:?0}}")
    public Optional<List<Book>> findBookByAuthorList(List<String> authors);

    public Optional<Book> deleteBookByIsbn(String isbn);

    public boolean existsBookByIsbn(String isbn);
}
