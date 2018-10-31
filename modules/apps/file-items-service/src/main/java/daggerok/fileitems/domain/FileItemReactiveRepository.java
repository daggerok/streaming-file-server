package daggerok.fileitems.domain;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileItemReactiveRepository {

  final FileItemRepository fileItemRepository;

  public Flux<FileItem> findAny(final String filename) {
    @Cleanup final Stream<FileItem> stream = fileItemRepository.findByFilenameContainingIgnoreCase(filename);
    return Flux.fromIterable(stream.collect(toList()));
  }

  @Transactional
  public Mono<FileItem> saveOrUpdate(final FileItem fileItem) {
    final FileItem curr = fileItemRepository.findByPathNotNullAndPathAndFilenameNotNullAndFilenameAndExtensionAndFileTypeNotNullAndFileTypeAndSizeNotNullAndSizeAndPrettySizeNotNullAndPrettySizeAndOwner(
        fileItem.path,
        fileItem.filename,
        fileItem.extension,
        fileItem.fileType,
        fileItem.size,
        fileItem.prettySize,
        fileItem.owner
    );
    return (null == curr)
        ? Mono.just(fileItemRepository.save(fileItem))
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

  @Transactional
  public Flux<FileItem> saveOrUpdateAll(final List<FileItem> fileItems) {
    return Flux.fromIterable(fileItems)
               .map(this::saveOrUpdate)
               .flatMap(i -> i);
  }
}
