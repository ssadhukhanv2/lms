package com.ssadhukhanv2.lms.librarymanagementsystem.monitor;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;


@Endpoint(id = "custom")
@Component
public class CustomActuatorEndpoint {


    //Custom Actuator Endpoint available at
    // http://localhost:8080/actuator/custom?id=23&name=ui
    @ReadOperation
    public Map<String, String> customEndpoint(String id, String name) {
        Map<String, String> map = new HashMap<>();
        map.put(id, name);
        return map;
    }

}
