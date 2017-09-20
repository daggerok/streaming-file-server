package com.daggerok.spring.streaming.fileserver.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface FileItemRepository extends JpaRepository<FileItem, Long>, QueryDslPredicateExecutor<FileItem> {

  Optional<FileItem> findById(@Param("id") final long id);

  Stream<FileItem> findByFilenameContainingIgnoreCase(@Param("filename") final String filename);
}
