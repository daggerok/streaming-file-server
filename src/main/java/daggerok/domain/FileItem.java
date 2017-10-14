package daggerok.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileItem implements Serializable {

  private static final long serialVersionUID = -6455969576919191029L;

  @NotNull
  Long id;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
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
