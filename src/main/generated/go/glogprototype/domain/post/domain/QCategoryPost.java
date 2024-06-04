package go.glogprototype.domain.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategoryPost is a Querydsl query type for CategoryPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoryPost extends EntityPathBase<CategoryPost> {

    private static final long serialVersionUID = 1739797301L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCategoryPost categoryPost = new QCategoryPost("categoryPost");

    public final QCategory category;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPost post;

    public QCategoryPost(String variable) {
        this(CategoryPost.class, forVariable(variable), INITS);
    }

    public QCategoryPost(Path<? extends CategoryPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCategoryPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCategoryPost(PathMetadata metadata, PathInits inits) {
        this(CategoryPost.class, metadata, inits);
    }

    public QCategoryPost(Class<? extends CategoryPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
    }

}

