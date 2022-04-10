package com.ssadhukhanv2.lms.librarymanagementsystem.factory;

import com.ssadhukhanv2.lms.librarymanagementsystem.dao.BookDao;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookDaoFactory {
    private static Lorem lorem = LoremIpsum.getInstance();
    private static Random random = new Random();

    private BookDaoFactory() {
        super();
    }

    //Default BookDao List
    public static List<BookDao> createDefaultBookDaoList() {
        List<BookDao> bookDaoList = new ArrayList<>();
        bookDaoList.add(BookDaoFactory.createBookDao(
                "Java: The Complete Reference, Twelfth Edition, 12th Edition",
                List.of("Herbert Schildt"),
                874.90,
                1280,
                2021,
                "McGraw-Hill"));
        bookDaoList.add(BookDaoFactory.createBookDao(
                "Spring Batch in Action",
                List.of("Arnaud Cogoluegnes", "Thierry Templier", "Gary Gregory", "Olivier Bazoud"),
                854.90,
                504,
                2011,
                "Manning Publications"));
        bookDaoList.add(BookDaoFactory.createBookDao(
                "JUnit in Action, Third Edition",
                List.of("Catalin Tudose"),
                585.60,
                560,
                2021,
                "Manning Publications"));
        bookDaoList.add(BookDaoFactory.createBookDao(
                "Apache Maven Cookbook",
                List.of("Raghuram Bharathan"),
                390.90,
                272,
                2015,
                "Packt Publishing"));
        bookDaoList.add(BookDaoFactory.createBookDao(
                "Spring Security in Action",
                List.of(" Laurentiu Spilca"),
                724.90,
                560,
                2020,
                "McGraw-Hill"));
        bookDaoList.add(BookDaoFactory.createBookDao(
                "Head First Design Patterns, 2nd Edition",
                List.of("Herbert Schildt"),
                874.90,
                669,
                2020,
                "O'Reilly Media, Inc."));
        return bookDaoList;
    }

    //Create a template BookDao objects
    public static BookDao createBookDao(final String bookName,
                                        final List<String> authorList,
                                        final double price,
                                        int pageCount,
                                        final int publicationYear,
                                        final String publisher) {
        BookDao bookDao = new BookDao();
        bookDao.setName(bookName);
        bookDao.setAuthorList(authorList);
        bookDao.setRating(getRandomDouble(3, 4));
        bookDao.setPrice(BigDecimal.valueOf(price));
        bookDao.setCurrency("ZAR");
        bookDao.setDescription(generateRandomParagraphs(1, 3));
        bookDao.setPublisher(publisher);
        bookDao.setPageCount(pageCount);
        bookDao.setGenres(getRandomGenres());
        bookDao.setIsbn(getRandomIsbn());
        bookDao.setLanguage("English");
        bookDao.setPublishedDate(getDateStringFromYear(publicationYear));
        bookDao.setNumberOfAvailableCopies(new BigInteger(String.valueOf(getRandomInteger(2, 10))));
        bookDao.setVoters(BigInteger.valueOf(getRandomInteger(500, 1000)));

        return bookDao;
    }

    //Generates a random integer between two range
    private static int getRandomInteger(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    //Generates a random double between two range
    private static double getRandomDouble(int min, int max) {
        return min + Math.random() * ((max - min) + 1);
    }


    private static String generateRandomParagraphs(int min, int max) {
        return lorem.getParagraphs(min, max);
    }

    //Generate a random isbn number
    private static String getRandomIsbn() {
        Long number = ThreadLocalRandom.current().nextLong(10000000000000L, 99999999999999L);
        return number.toString();
    }


    //Generate a random collection of genres
    private static List<String> getRandomGenres() {
        List<String> genreList = Stream.of("engineering"
                , "coding", "self-improvement"
                , "devops", "java"
                , "microservices", "design-patterns",
                "software-architecture", "domain-driven-design",
                "agile", "containerization").collect(Collectors.toList());
        Collections.shuffle(genreList, new Random(5));
        return genreList.subList(getRandomInteger(1, 2), getRandomInteger(6, 8));
    }

    //Generates a random date given a year
    private static String getDateStringFromYear(int year) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getRandomInteger(1, 28) + "/");
        stringBuilder.append(getRandomInteger(1, 12) + "/");
        stringBuilder.append(year);
        return stringBuilder.toString();
    }
}
