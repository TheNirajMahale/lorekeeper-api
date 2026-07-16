package com.lorekeeper.lorekeeper_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LorekeeperApiApplicationTests {

    @org.springframework.beans.factory.annotation.Autowired
    private com.lorekeeper.lorekeeper_api.service.OpenLibraryService openLibraryService;

    @Test
    void contextLoads() {
        try {
            System.out.println("TESTING OPENLIBRARY API...");
            var results = openLibraryService.searchWorks("way of kings");
            System.out.println("RESULTS: " + results.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
