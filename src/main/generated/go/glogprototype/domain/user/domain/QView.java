package go.glogprototype.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QView is a Querydsl query type for View
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QView extends EntityPathBase<View> {

    private static final long serialVersionUID = 868459719L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QView view = new QView("view");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final go.glogprototype.domain.post.domain.QPost post;

    public final DateTimePath<java.time.LocalDateTime> updatedTime = createDateTime("updatedTime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> view_count = createNumber("view_count", Integer.class);

    public QView(String variable) {
        this(View.class, forVariable(variable), INITS);
    }

    public QView(Path<? extends View> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QView(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QView(PathMetadata metadata, PathInits inits) {
        this(View.class, metadata, inits);
    }

    public QView(Class<? extends View> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.post = inits.isInitialized("post") ? new go.glogprototype.domain.post.domain.QPost(forProperty("post"), inits.get("post")) : null;
    }

}

