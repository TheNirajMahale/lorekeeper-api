package com.lorekeeper.lorekeeper_api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class RoyalRoadService {

    public String extractFictionId(String royalRoadUrl) {
        String[] parts = royalRoadUrl.split("/");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("fiction")) {
                return parts[i + 1];
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Royal Road URL");
    }
}