package daggerok.fileitems.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import daggerok.fileitems.domain.audit.AbstractAuditEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

import static daggerok.fileitems.domain.FileType.FILE;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.NONE;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true, exclude = { "version", "prettySize" })
public class FileItem extends AbstractAuditEntity {

  private static final long serialVersionUID = -6455969576919191029L;
  private static final int Kib = 1024;
  private static final long NORMAL_FILE_SIZE = 2 * Kib * Kib;

  @Id
  @Setter(NONE)
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = SEQUENCE, generator = "fileItemSeq")
  @SequenceGenerator(name = "fileItemSeq", sequenceName = "file_item_id_seq", allocationSize = 1)
  private Long id;

  @NonNull private String path;
  @NonNull private String filename;
  private String extension;
  @NonNull private String prettySize;
  @NonNull private Long size;

  @NonNull
  @Enumerated(STRING)
  private FileType fileType;

  @Version private Long version;
  private String owner;

  @Transient
  public boolean isFile() {
    return FILE.equals(this.fileType);
  }

  @Transient
  public boolean isLarge() {
    return this.size > NORMAL_FILE_SIZE;
  }
}
