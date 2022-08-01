package com.load.filter.load_filter_platform;

import com.load.filter.load_filter_platform.util.SetupDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class LoadFilterPlatformApplication {

    private final SetupDBService setupDBService;


    public static void main(String[] args) {
        SpringApplication.run(LoadFilterPlatformApplication.class, args);
    }


    @PostConstruct
    public void init() {
        setupDBService.setup();
    }
}
