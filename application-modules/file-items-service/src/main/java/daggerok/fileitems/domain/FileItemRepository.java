package daggerok.fileitems.domain;

import lombok.Cleanup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Repository
public interface FileItemRepository extends JpaRepository<FileItem, Long> {

  /* private */
  Stream<FileItem> findByFilenameContainingIgnoreCase(@Param("filename") final String filename);

  default Flux<FileItem> findAny(final String filename) {
    @Cleanup final Stream<FileItem> stream = findByFilenameContainingIgnoreCase(filename);
    return Flux.fromIterable(stream.collect(toList()));
  }

  /* private */
  FileItem findByPathNotNullAndPathAndFilenameNotNullAndFilenameAndExtensionAndFileTypeNotNullAndFileTypeAndSizeNotNullAndSizeAndPrettySizeNotNullAndPrettySizeAndOwner(
      @Param("path") final String path,
      @Param("filename") final String filename,
      @Param("extension") final String extension,
      @Param("fileType") final FileType fileType,
      @Param("size") final Long size,
      @Param("prettySize") final String prettySize,
      @Param("owner") final String owner
  );

  default Mono<FileItem> saveOrUpdate(final FileItem fileItem) {

    final FileItem curr = findByPathNotNullAndPathAndFilenameNotNullAndFilenameAndExtensionAndFileTypeNotNullAndFileTypeAndSizeNotNullAndSizeAndPrettySizeNotNullAndPrettySizeAndOwner(
        fileItem.path,
        fileItem.filename,
        fileItem.extension,
        fileItem.fileType,
        fileItem.size,
        fileItem.prettySize,
        fileItem.owner
    );

    return (null == curr)
        ? Mono.just(save(fileItem))
        : Mono.just(curr)
              .map(c -> c.setSize(ofNullable(fileItem.size).orElse(c.size))
                         .setExtension(ofNullable(fileItem.extension).orElse(fileItem.extension))
                         .setFilename(ofNullable(fileItem.filename).orElse(fileItem.filename))
                         .setPath(ofNullable(fileItem.path).orElse(fileItem.path))
                         .setFileType(ofNullable(fileItem.fileType).orElse(fileItem.fileType))
                         .setPrettySize(ofNullable(fileItem.prettySize).orElse(fileItem.prettySize))
                         .setOwner(ofNullable(fileItem.owner).orElse(fileItem.owner))
              )
        ;
  }

  default Flux<FileItem> saveOrUpdateAll(final List<FileItem> fileItems) {

    return Flux.fromIterable(fileItems)
               .map(this::saveOrUpdate)
               .flatMap(i -> i);
  }
}
