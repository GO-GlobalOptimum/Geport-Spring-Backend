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

    public final NumberPath<Integer> bookMarkCount = createNumber("bookMarkCount", Integer.class);

    public final ListPath<BookMark, QBookMark> bookMarks = this.<BookMark, QBookMark>createList("bookMarks", BookMark.class, QBookMark.class, PathInits.DIRECT2);

    public final ListPath<CategoryPost, QCategoryPost> categoryPostList = this.<CategoryPost, QCategoryPost>createList("categoryPostList", CategoryPost.class, QCategoryPost.class, PathInits.DIRECT2);

    public final NumberPath<Integer> commentCount = createNumber("commentCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isComment = createBoolean("isComment");

    public final BooleanPath isDelete = createBoolean("isDelete");

    public final BooleanPath isPublic = createBoolean("isPublic");

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final go.glogprototype.domain.user.domain.QMember member;

    public final StringPath postContent = createString("postContent");

    public final ListPath<PostTag, QPostTag> tags = this.<PostTag, QPostTag>createList("tags", PostTag.class, QPostTag.class, PathInits.DIRECT2);

    public final StringPath thumbnailImage = createString("thumbnailImage");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final ListPath<go.glogprototype.domain.user.domain.View, go.glogprototype.domain.user.domain.QView> views = this.<go.glogprototype.domain.user.domain.View, go.glogprototype.domain.user.domain.QView>createList("views", go.glogprototype.domain.user.domain.View.class, go.glogprototype.domain.user.domain.QView.class, PathInits.DIRECT2);

    public final NumberPath<Integer> viewsCount = createNumber("viewsCount", Integer.class);

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

