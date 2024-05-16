package go.glogprototype.domain.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -1683941353L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final ListPath<BookMark, QBookMark> bookMarks = this.<BookMark, QBookMark>createList("bookMarks", BookMark.class, QBookMark.class, PathInits.DIRECT2);

    public final NumberPath<Integer> comment_count = createNumber("comment_count", Integer.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath is_comment = createBoolean("is_comment");

    public final BooleanPath is_delete = createBoolean("is_delete");

    public final BooleanPath is_public = createBoolean("is_public");

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final go.glogprototype.domain.user.domain.QMember member;

    public final StringPath post_content = createString("post_content");

    public final StringPath thumbnail_image = createString("thumbnail_image");

    public final StringPath thumbnail_text = createString("thumbnail_text");

    public final StringPath title = createString("title");

    public final NumberPath<Integer> views_count = createNumber("views_count", Integer.class);

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new go.glogprototype.domain.user.domain.QMember(forProperty("member")) : null;
    }

}

