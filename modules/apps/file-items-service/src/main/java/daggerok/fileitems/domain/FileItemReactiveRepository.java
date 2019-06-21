package daggerok.fileitems.domain;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
// TODO: FIXME: JPA Transactional manager doesn't worked anymore...
// @Transactional(readOnly = true)//("transactionManager") // for some reasons its looking for reactiveTransactionManager
public class FileItemReactiveRepository {

  private final FileItemRepository fileItemRepository;

  public Flux<FileItem> findAny(final String filename) {
    @Cleanup final Stream<FileItem> stream = fileItemRepository.findByFilenameContainingIgnoreCase(filename);
    return Flux.fromIterable(stream.collect(toList()));
  }

  // @Transactional
  // TODO: FIXME: JPA Transactional manager doesn't worked anymore...
  public Mono<FileItem> saveOrUpdate(final FileItem fileItem) {
    final FileItem curr =
        fileItemRepository.findByPathNotNullAndPathAndFilenameNotNullAndFilenameAndExtensionAndFileTypeNotNullAndFileTypeAndSizeNotNullAndSizeAndPrettySizeNotNullAndPrettySizeAndOwner(
        fileItem.getPath(),
        fileItem.getFilename(),
        fileItem.getExtension(),
        fileItem.getFileType(),
        fileItem.getSize(),
        fileItem.getPrettySize(),
        fileItem.getOwner()
    );
    return Objects.isNull(curr)
        ? Mono.just(fileItemRepository.save(fileItem))
        : Mono.just(curr)
              .map(c -> c.setSize(ofNullable(fileItem.getSize()).orElse(c.getSize()))
                         .setExtension(ofNullable(fileItem.getExtension()).orElse(fileItem.getExtension()))
                         .setFilename(ofNullable(fileItem.getFilename()).orElse(fileItem.getFilename()))
                         .setPath(ofNullable(fileItem.getPath()).orElse(fileItem.getPath()))
                         .setFileType(ofNullable(fileItem.getFileType()).orElse(fileItem.getFileType()))
                         .setPrettySize(ofNullable(fileItem.getPrettySize()).orElse(fileItem.getPrettySize()))
                         .setOwner(ofNullable(fileItem.getOwner()).orElse(fileItem.getOwner())))
        ;
  }

  // @Transactional
  // TODO: FIXME: JPA Transactional manager doesn't worked anymore...
  public Flux<FileItem> saveOrUpdateAll(final List<FileItem> fileItems) {
    return Flux.fromIterable(fileItems)
               .map(this::saveOrUpdate)
               .flatMap(i -> i);
  }
}
