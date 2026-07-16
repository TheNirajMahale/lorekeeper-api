package com.lorekeeper.lorekeeper_api.controller;

import com.lorekeeper.lorekeeper_api.dto.ChapterResponseDTO;
import com.lorekeeper.lorekeeper_api.entity.Chapter;
import com.lorekeeper.lorekeeper_api.entity.Novel;
import com.lorekeeper.lorekeeper_api.repository.ChapterRepository;
import com.lorekeeper.lorekeeper_api.repository.NovelRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/novels/{novelId}/chapters")
public class ChapterController {

    private final ChapterRepository chapterRepository;
    private final NovelRepository novelRepository;

    public ChapterController(ChapterRepository chapterRepository, NovelRepository novelRepository) {
        this.chapterRepository = chapterRepository;
        this.novelRepository = novelRepository;
    }

    @GetMapping
    public List<ChapterResponseDTO> getChapters(@PathVariable Long novelId) {
        return chapterRepository.findByNovelId(novelId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{chapterId}")
    public ChapterResponseDTO getChapter(@PathVariable Long novelId, @PathVariable Long chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chapter not found"));
        return toDTO(chapter);
    }

    @PostMapping
    public ChapterResponseDTO createChapter(@PathVariable Long novelId, @RequestBody Chapter chapter) {
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Novel not found"));
        chapter.setNovel(novel);
        Chapter saved = chapterRepository.save(chapter);
        return toDTO(saved);
    }

    @PatchMapping("/{chapterId}")
    public ChapterResponseDTO markRead(@PathVariable Long novelId, @PathVariable Long chapterId,
                                       @RequestBody ReadStatusRequest request) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chapter not found"));
        chapter.setRead(request.isRead());
        Chapter updated = chapterRepository.save(chapter);
        return toDTO(updated);
    }

    @DeleteMapping("/{chapterId}")
    public void deleteChapter(@PathVariable Long novelId, @PathVariable Long chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chapter not found"));
        chapterRepository.deleteById(chapterId);
    }

    private ChapterResponseDTO toDTO(Chapter chapter) {
        ChapterResponseDTO dto = new ChapterResponseDTO();
        dto.setId(chapter.getId());
        dto.setTitle(chapter.getTitle());
        dto.setChapterNo(chapter.getChapterNo());
        dto.setLink(chapter.getLink());
        dto.setTime(chapter.getTime());
        dto.setRead(chapter.isRead());
        dto.setNovelId(chapter.getNovel().getId());
        return dto;
    }

    public static class ReadStatusRequest {
        private boolean read;

        public boolean isRead() {
            return read;
        }

        public void setRead(boolean read) {
            this.read = read;
        }
    }
}