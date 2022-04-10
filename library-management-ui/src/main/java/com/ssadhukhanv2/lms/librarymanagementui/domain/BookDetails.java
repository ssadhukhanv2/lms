package com.ssadhukhanv2.lms.librarymanagementui.domain;

import lombok.Data;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Data
public class BookDetails {
    //Title of the Book
    private String name;

    //Names of the authors of the book
    private List<String> authorList;

    //Rating out of 5
    private double rating;

    //Number of voters who voted for the book, ratings would be calculated based on these votes
    private long voters;

    //Price of the book
    private double price;

    //Monthly rent for the book
    private double monthlyRent;

    //Currency for the price & monthly rent for book
    private String currency;

    //Description of the book
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
    private long numberOfAvailableCopies;


    //Number of available copies in the library
    private boolean available;
}
