package daggerok.fileitems.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QFileItem is a Querydsl query type for FileItem
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFileItem extends EntityPathBase<FileItem> {

    private static final long serialVersionUID = 1268445575L;

    public static final QFileItem fileItem = new QFileItem("fileItem");

    public final daggerok.fileitems.domain.audit.QAbstractAuditEntity _super = new daggerok.fileitems.domain.audit.QAbstractAuditEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath extension = createString("extension");

    public final StringPath filename = createString("filename");

    public final EnumPath<FileType> fileType = createEnum("fileType", FileType.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath owner = createString("owner");

    public final StringPath path = createString("path");

    public final StringPath prettySize = createString("prettySize");

    public final NumberPath<Long> size = createNumber("size", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QFileItem(String variable) {
        super(FileItem.class, forVariable(variable));
    }

    public QFileItem(Path<? extends FileItem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFileItem(PathMetadata metadata) {
        super(FileItem.class, metadata);
    }

}

