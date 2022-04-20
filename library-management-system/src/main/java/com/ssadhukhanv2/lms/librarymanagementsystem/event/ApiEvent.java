package com.ssadhukhanv2.lms.librarymanagementsystem.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiEvent {
    private String method;
    private String uri;
    private int responseStatus;
}
