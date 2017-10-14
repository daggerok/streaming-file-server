package daggerok.fileitems.domain.audit;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAbstractAuditEntity is a Querydsl query type for AbstractAuditEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QAbstractAuditEntity extends EntityPathBase<AbstractAuditEntity> {

    private static final long serialVersionUID = 1069381553L;

    public static final QAbstractAuditEntity abstractAuditEntity = new QAbstractAuditEntity("abstractAuditEntity");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QAbstractAuditEntity(String variable) {
        super(AbstractAuditEntity.class, forVariable(variable));
    }

    public QAbstractAuditEntity(Path<? extends AbstractAuditEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAbstractAuditEntity(PathMetadata metadata) {
        super(AbstractAuditEntity.class, metadata);
    }

}

