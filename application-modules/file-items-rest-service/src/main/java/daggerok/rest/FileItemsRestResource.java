package daggerok.rest;

import daggerok.domain.FileItem;
import daggerok.domain.FileItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
