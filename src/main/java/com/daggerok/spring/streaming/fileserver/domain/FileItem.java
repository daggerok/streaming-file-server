package com.daggerok.spring.streaming.fileserver.domain;

import com.daggerok.spring.streaming.fileserver.domain.audit.AbstractAuditEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.persistence.Version;

import static com.daggerok.spring.streaming.fileserver.domain.FileType.FILE;
import static com.daggerok.spring.streaming.fileserver.service.util.FileItemUtil.NORMAL_FILE_SIZE;
import static javax.persistence.EnumType.STRING;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileItem extends AbstractAuditEntity {

    private static final long serialVersionUID = -6455969576919191029L;

    @NonNull
    String path;

    String filename;

    String extension;

    @NonNull
    String prettySize;

    @NonNull
    Long size;

    @NonNull
    @Enumerated(STRING)
    FileType fileType;

    @Version
    Long version;

    String owner;

    @Transient
    public boolean isFile() {
        return FILE.equals(this.fileType);
    }

    @Transient
    public boolean isLarge() {
        return this.size > NORMAL_FILE_SIZE;
    }
}
