package com.lorekeeper.lorekeeper_api.repository;

import com.lorekeeper.lorekeeper_api.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findByNovelId(Long novelId);
}