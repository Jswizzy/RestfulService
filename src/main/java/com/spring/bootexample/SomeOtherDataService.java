package com.spring.bootexample;

import com.spring.bootexample.common.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SomeOtherDataService {
    @Qualifier("application")
    @Autowired
    private ApplicationConfiguration configuration;

    public String retrieveData() {
        System.out.println(configuration.getService1Timeout());
        System.out.println(configuration.getService1Url());
        System.out.println(configuration.isEnableSwitchForService1());
        return "Data from service";
    }
}
