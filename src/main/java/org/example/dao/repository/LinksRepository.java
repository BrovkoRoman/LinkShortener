package org.example.dao.repository;

import org.example.dao.LinksDao;
import org.example.dao.entity.LinksEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LinksRepository extends JpaRepository<LinksEntity, String> {
    @Query(nativeQuery = true, value = "SELECT short_code, long_link FROM links WHERE short_code = ?1")
    @Cacheable(value = "LinksRepository::findByShortCode", key = "#shortCode")
    Optional<String[]> findByShortCode(String shortCode);
    @Query(nativeQuery = true, value = "SELECT short_code, long_link FROM links WHERE long_link = ?1")
    @Cacheable(value = "LinksRepository::findByLongLink", key = "#longLink")
    Optional<String[]> findByLongLink(String longLink);
}
