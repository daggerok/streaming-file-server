package com.daggerok.spring.streaming.fileserver.domain.audit;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@Accessors(chain = true)
@EqualsAndHashCode(exclude = "id")
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditEntity implements Serializable {

  private static final long serialVersionUID = 9207056121666919093L;

  @Id
  @GeneratedValue
  Long id;

  @CreatedDate
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:s")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  LocalDateTime createdAt;

  @LastModifiedDate
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:s.SSS")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  LocalDateTime updatedAt;
}
