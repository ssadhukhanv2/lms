package com.ssadhukhanv2.lms.librarymanagementui.mapper;

import com.ssadhukhanv2.lms.librarymanagementui.client.domain.BookDao;
import com.ssadhukhanv2.lms.librarymanagementui.domain.BookDetails;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;


@Mapper(componentModel = "spring")
public interface BookMapper {

    public BookDetails map(BookDao bookDao);

    public List<BookDetails> map(List<BookDao> bookDao);


}
