package com.lorekeeper.lorekeeper_api.controller;

import com.lorekeeper.lorekeeper_api.dto.ScrapedMangaDto;
import com.lorekeeper.lorekeeper_api.service.MangaHubScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scraper")
public class ScraperController {

    private final MangaHubScraperService scraperService;

    @Autowired
    public ScraperController(MangaHubScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @GetMapping("/mangahub")
    public ResponseEntity<ScrapedMangaDto> scrapeMangaHub(@RequestParam String url) {
        if (url == null || url.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        ScrapedMangaDto dto = scraperService.scrapeManga(url);
        return ResponseEntity.ok(dto);
    }
}
