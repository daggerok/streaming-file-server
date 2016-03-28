package com.daggerok.spring.streaming.fileserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.daggerok.spring.streaming.fileserver.domain.FileType.FILE;
import static com.daggerok.spring.streaming.fileserver.service.util.FileItemUtil.NORMAL_FILE_SIZE;
import static javax.persistence.GenerationType.AUTO;


@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor(staticName = "from")
public class FileItem implements Serializable {

    private static final long serialVersionUID = -6455969576919191029L;

    @Id
    @GeneratedValue(strategy = AUTO)
    Long id;

    @NonNull
    String path;
    
    String filename;

    String extension;

    @NonNull
    String prettySize;

    @NonNull
    Long size;

    @NonNull
    @Enumerated(EnumType.STRING)
    FileType fileType;

    @Version
    Long version;

    LocalDateTime updatedAt = LocalDateTime.now();

    @Transient
    public boolean isFile() {
        return FILE.equals(this.fileType);
    }

    @Transient
    public boolean isLarge() {
        return this.size > NORMAL_FILE_SIZE;
    }
}
