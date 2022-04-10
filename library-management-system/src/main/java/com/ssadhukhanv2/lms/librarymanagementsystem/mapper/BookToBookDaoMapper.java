package com.ssadhukhanv2.lms.librarymanagementsystem.mapper;

import com.ssadhukhanv2.lms.librarymanagementsystem.dao.BookDao;
import com.ssadhukhanv2.lms.librarymanagementsystem.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;


@Mapper(componentModel = "spring")
public interface BookToBookDaoMapper {
    @Mapping(source = "title", target = "name")
    @Mapping(source = "price", target = "monthlyRent", qualifiedByName = "calculateRentFromPrice")
    @Mapping(source = "publishedDate", target = "publishedDate", dateFormat = "dd/MM/yyyy")
    @Mapping(source = "numberOfAvailableCopies", target = "available", qualifiedByName = "determineAvailable")
    public BookDao map(Book book);


    @Mapping(source = "name", target = "title")
    @Mapping(source = "publishedDate", target = "publishedDate", dateFormat = "dd/MM/yyyy")
    public Book map(BookDao bookDao);


    public List<BookDao> map(List<Book> bookList);

    public List<Book> mapToBookList(List<BookDao> defaultBookDaoList);

    @Named("calculateRentFromPrice")
    public static BigDecimal calculateRentFromPrice(BigDecimal price) {
        //The monthly rent of books is calculated as 1/24 th of the price
        // Fixed Arithmetic Exception:Non-terminating decimal expansion; no exact representable decimal result.
        // https://stackoverflow.com/questions/4591206/arithmeticexception-non-terminating-decimal-expansion-no-exact-representable
        // https://jaydeepm.wordpress.com/2009/06/04/bigdecimal-and-non-terminating-decimal-expansion-error/
        return price.divide(new BigDecimal(24.00), 2, RoundingMode.CEILING);
    }

    @Named("determineAvailable")
    public static boolean determineAvailable(BigInteger numberOfAvailableCopies) {
        //If the number of available copies is greater than 0, book is available(true) else book is NOT available(false)
        return numberOfAvailableCopies.compareTo(new BigInteger("0")) > 0 ? true : false;
    }


}

