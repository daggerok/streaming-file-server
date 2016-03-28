package com.daggerok.spring.streaming.fileserver.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

@RepositoryRestResource
public interface FileItemRepository extends JpaRepository<FileItem, Long> {
    Optional<FileItem> findById(@Param("id") long id);

    Stream<FileItem> findByFilenameLikeIgnoreCase(@Param("filename") String filename);

    Stream<FileItem> findBySizeGreaterThanEqual(@Param("size") long size);

    Stream<FileItem> findBySizeBetween(@Param("from") long from, @Param("to") long to);

    Stream<FileItem> findBySizeLessThanEqual(@Param("size") long size);

    Stream<FileItem> findByUpdatedAtBefore(@Param("updatedAt") LocalDateTime updatedAt);

    Stream<FileItem> findByUpdatedAtBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    Stream<FileItem> findByUpdatedAtAfter(@Param("updatedAt") LocalDateTime updatedAt);
}
