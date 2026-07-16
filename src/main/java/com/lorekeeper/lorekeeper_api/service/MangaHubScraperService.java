package com.lorekeeper.lorekeeper_api.service;

import com.lorekeeper.lorekeeper_api.dto.ScrapedMangaDto;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitUntilState;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MangaHubScraperService {

    public ScrapedMangaDto scrapeManga(String url) {
        // We instantiate Playwright per request for simplicity and thread safety in this prototype.
        // In a high-throughput production app, consider using a pool of headless browsers.
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            Page page = browser.newPage();
            
            // Navigate to the URL and wait until there are no more than 2 network connections for at least 500 ms.
            // This is crucial for bypassing Cloudflare's initial checks which rely on multiple redirects/scripts.
            page.navigate(url, new Page.NavigateOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));
            
            // Wait an additional 3 seconds to ensure Cloudflare's JS challenge is fully completed.
            page.waitForTimeout(3000);

            // Extracting details - note that these CSS selectors are generic examples 
            // and might need to be adjusted based on MangaHub's actual HTML structure.
            String title = extractText(page, "h1"); 
            String author = extractText(page, ".author, .manga-author, .text-secondary"); // common classes
            String status = extractText(page, ".status, .manga-status"); 
            
            List<String> chaptersList = new ArrayList<>();
            Locator chapters = page.locator(".list-group-item a, .chapter-list a, .list-chapter a");
            for (int i = 0; i < chapters.count(); i++) {
                chaptersList.add(chapters.nth(i).innerText().trim());
            }

            String rawHtml = page.content();

            browser.close();

            return new ScrapedMangaDto(title, author, status, chaptersList, rawHtml);
        } catch (Exception e) {
            throw new RuntimeException("Failed to scrape manga from URL: " + url, e);
        }
    }

    private String extractText(Page page, String selector) {
        try {
            Locator locator = page.locator(selector).first();
            if (locator.count() > 0) {
                return locator.innerText().trim();
            }
        } catch (Exception e) {
            // Element not found or error extracting text, ignore and return unknown
        }
        return "Unknown";
    }
}
