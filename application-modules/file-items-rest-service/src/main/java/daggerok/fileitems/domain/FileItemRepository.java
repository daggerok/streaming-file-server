package daggerok.fileitems.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface FileItemRepository extends JpaRepository<FileItem, Long>, QueryDslPredicateExecutor<FileItem> {
  Stream<FileItem> findByFilenameContainingIgnoreCase(@Param("filename") final String filename);
}
