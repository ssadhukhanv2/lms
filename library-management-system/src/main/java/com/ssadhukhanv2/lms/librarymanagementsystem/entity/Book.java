package com.ssadhukhanv2.lms.librarymanagementsystem.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;


/*
* Entity Object for Book
* */
@Data
@Document(collection = "book")
public class Book {

    //Identifier for each book document the mongodb collection
    @Id
    private BigInteger id;

    //Title of the Book
    private String title;

    //Names of the authors of the book
    @Field("authors")
    private List<String> authorList;

    //Rating out of 5
    private double rating;

    //Number of voters who voted for the book, ratings would be calculated based on these votes
    private BigInteger voters;

    //Price of the book
    private BigDecimal price;

    //Currency for the price
    private String currency;

    //Description of the book
    private String description;

    //Publisher of the book
    private String publisher;

    //Published Date of the book
    @Field("published_date")
    private Date publishedDate;

    //Number of Pages in the book
    @Field("page_count")
    private int pageCount;

    //Genres of the book
    private List<String> genres;

    //International Standard Book Number for the book
    @Indexed(unique = true)
    private String isbn;

    //Language of the book
    private String language;

    //Number of available copies in the library
    @Field("available_copies")
    private BigInteger numberOfAvailableCopies;

}
