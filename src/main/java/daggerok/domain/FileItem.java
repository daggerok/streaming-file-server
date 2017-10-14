package daggerok.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

import static daggerok.domain.FileType.FILE;
import static daggerok.service.util.FileItemUtil.NORMAL_FILE_SIZE;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileItem implements Serializable {

  private static final long serialVersionUID = -6455969576919191029L;

  @NotNull
  Long id;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:s")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:s.SSS")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  LocalDateTime updatedAt;

  @NonNull
  String path;

  String filename;

  String extension;

  @NonNull
  String prettySize;

  @NonNull
  Long size;

  @NonNull
  FileType fileType;

  Long version;

  String owner;

  public boolean isFile() {
    return FILE.equals(this.fileType);
  }

  public boolean isLarge() {
    return this.size > NORMAL_FILE_SIZE;
  }
}
