package com.ssadhukhanv2.lms.librarymanagementsystem.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Domain object for Book
 *
 * @author Subhrajit Sadhukhan
 */

@Data
@NoArgsConstructor
public class BookDao {

    //Title of the Book
    private String name;

    //Names of the authors of the book
    private List<String> authorList;

    //Rating out of 5
    private double rating;

    //Number of voters who voted for the book, ratings would be calculated based on these votes
    private BigInteger voters;

    //Price of the book
    private BigDecimal price;

    //Monthly rent for the book
    private BigDecimal monthlyRent;

    //Currency for the price & monthly rent for book
    private String currency;

    //Description of the book
    @ToString.Exclude
    private String description;

    //Publisher of the book
    private String publisher;

    //Number of Pages in the book
    private int pageCount;

    //Genres of the book
    private List<String> genres;

    //International Standard Book Number for the book
    private String isbn;

    //Language of the book
    private String language;

    //Published Date of the book
    private String publishedDate;

    //Number of available copies in the library
    private BigInteger numberOfAvailableCopies;


    //Number of available copies in the library
    private boolean available;
}
