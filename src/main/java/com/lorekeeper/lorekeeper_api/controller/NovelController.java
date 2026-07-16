package com.lorekeeper.lorekeeper_api.controller;

import com.lorekeeper.lorekeeper_api.dto.NovelResponseDTO;
import com.lorekeeper.lorekeeper_api.entity.Novel;
import com.lorekeeper.lorekeeper_api.repository.NovelRepository;
import com.lorekeeper.lorekeeper_api.service.RoyalRoadService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController   // Combines @Controller + @ResponseBody: return values get written directly as JSON
public class NovelController {

    // Spring injects a real NovelRepository instance automatically (Dependency Injection) —
    // we never write "new NovelRepository()" ourselves.
    private final NovelRepository novelRepository;
    private final RoyalRoadService royalRoadService;
    public NovelController(NovelRepository novelRepository,RoyalRoadService royalRoadService) {
        this.novelRepository = novelRepository;
        this.royalRoadService = royalRoadService;
    }

    // GET /novels -> list every novel
    @GetMapping("/novels")
    public List<NovelResponseDTO> getNovels() {
        return novelRepository.findAll()
                .stream()
                .map(this::toDTO)   // convert each Novel entity into a NovelResponseDTO
                .collect(Collectors.toList());
    }

    // GET /novels/{id} -> one specific novel, 404 if it doesn't exist
    @GetMapping("/novels/{id}")
    public NovelResponseDTO getNovelById(@PathVariable Long id) {
        Novel novel = novelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Novel not found"));
        return toDTO(novel);
    }

    // POST /novels -> create a new novel from the request body's JSON
    @PostMapping("/novels")
    public NovelResponseDTO createNovel(@Valid @RequestBody Novel novel) {
        Novel saved = novelRepository.save(novel);
        return toDTO(saved);
    }
    @PostMapping("/novels/import")
    public NovelResponseDTO createNovel(@RequestBody ImportRequest request )
    {
        royalRoadService.extractFictionId(request.getRoyalRoadUrl());
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not implemented yet");
    }


    // PUT /novels/{id} -> full update: fetch existing novel (keeps its real id),
    // overwrite every field with the incoming data, save it back
    @PutMapping("/novels/{id}")
    public NovelResponseDTO editNovel(@PathVariable Long id, @RequestBody Novel nNovel) {
        Novel novel = novelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Novel not found"));

        novel.setTitle(nNovel.getTitle());
        novel.setAuthor(nNovel.getAuthor());
        novel.setSummary(nNovel.getSummary());
        novel.setImage(nNovel.getImage());

        Novel updated = novelRepository.save(novel);
        return toDTO(updated);
    }

    // DELETE /novels/{id} -> fetch first (so we can return what was deleted), then delete
    @DeleteMapping("/novels/{id}")
    public NovelResponseDTO deleteNovel(@PathVariable Long id) {
        Novel novel = novelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Novel not found"));
        novelRepository.deleteById(id);
        return toDTO(novel);
    }

    // Manual conversion: Novel (DB shape) -> NovelResponseDTO (client-facing shape).
    // Spring doesn't do this automatically — we write it once, reuse it everywhere above.
    private NovelResponseDTO toDTO(Novel novel) {
        NovelResponseDTO dto = new NovelResponseDTO();
        dto.setId(novel.getId());
        dto.setRoyalRoadId(novel.getRoyalRoadId());
        dto.setTitle(novel.getTitle());
        dto.setAuthor(novel.getAuthor());
        dto.setSummary(novel.getSummary());
        dto.setImage(novel.getImage());
        return dto;
    }

    public static class ImportRequest {
        private String royalRoadUrl;

        public String getRoyalRoadUrl() {
            return royalRoadUrl;
        }

        public void setRoyalRoadUrl(String royalRoadUrl) {
            this.royalRoadUrl = royalRoadUrl;
        }
    }
}