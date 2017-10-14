package daggerok.domain.audit;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(exclude = "id")
public abstract class AbstractAuditEntity implements Serializable {

  private static final long serialVersionUID = 9207056121666919093L;

  Long id;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:s")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:s.SSS")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  LocalDateTime updatedAt;
}
