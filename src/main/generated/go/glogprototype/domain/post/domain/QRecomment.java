package go.glogprototype.domain.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecomment is a Querydsl query type for Recomment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecomment extends EntityPathBase<Recomment> {

    private static final long serialVersionUID = 1704043445L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecomment recomment = new QRecomment("recomment");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QComment comment;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    public final go.glogprototype.domain.user.domain.QMember member;

    public final StringPath recomment_content = createString("recomment_content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QRecomment(String variable) {
        this(Recomment.class, forVariable(variable), INITS);
    }

    public QRecomment(Path<? extends Recomment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecomment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecomment(PathMetadata metadata, PathInits inits) {
        this(Recomment.class, metadata, inits);
    }

    public QRecomment(Class<? extends Recomment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new QComment(forProperty("comment"), inits.get("comment")) : null;
        this.member = inits.isInitialized("member") ? new go.glogprototype.domain.user.domain.QMember(forProperty("member")) : null;
    }

}

