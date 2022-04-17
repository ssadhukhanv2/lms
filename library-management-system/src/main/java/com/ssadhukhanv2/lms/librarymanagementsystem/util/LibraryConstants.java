package com.ssadhukhanv2.lms.librarymanagementsystem.util;


import java.text.DecimalFormat;
import java.util.List;

public class LibraryConstants {
    public static final List<String> SORT_KEY_LIST = List.of("title", "rating");
    // Since DecimalFormat is not thread safe we create only the string constant here
    public static final String DECIMAL_FORMAT_RESPONSE_TIME = "##.########";
}
