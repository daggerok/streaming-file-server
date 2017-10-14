package daggerok.rest;

import daggerok.domain.FileItem;
import daggerok.domain.FileItemRepository;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
@RequestMapping("/api/v1/file-items")
public class FileItemsRestResource {

  final FileItemRepository repository;

  @GetMapping
  public List<FileItem> getAll() {
    return repository.findAll();
  }

  @GetMapping("/like/{filename}")
  public List<FileItem> searchAny(@PathVariable("filename") final String filename) {
    @Cleanup val stream = repository.findByFilenameContainingIgnoreCase(filename);
    return stream.collect(toList());
  }

  @GetMapping("/{id}")
  public FileItem getById(@PathVariable("id") final String id) {
    return repository.findOne(Long.valueOf(id));
  }

  @PostMapping
  @Transactional
  @ResponseStatus(CREATED)
  public FileItem save(@RequestBody @Validated final FileItem fileItem) {
    return repository.save(fileItem);
  }

  @Transactional
  @PostMapping("/all")
  @ResponseStatus(CREATED)
  public List<FileItem> save(@RequestBody @Validated final List<FileItem> fileItems) {
    return repository.save(fileItems);
  }
}
