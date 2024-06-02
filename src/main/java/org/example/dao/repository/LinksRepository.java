package org.example.dao.repository;

import org.example.dao.entity.LinksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinksRepository extends JpaRepository<LinksEntity, String> {
    @Query(nativeQuery = true,
            value = "SELECT LONG_LINK FROM links WHERE SHORT_CODE = :shortCode")
    List<String> findLongLinksByShortCode(@Param("shortCode") String shortCode);
    @Query(nativeQuery = true,
            value = "SELECT SHORT_CODE FROM links WHERE LONG_LINK = :longLink")
    List<String> findShortCodesByLongLink(@Param("longLink") String longLink);
}
