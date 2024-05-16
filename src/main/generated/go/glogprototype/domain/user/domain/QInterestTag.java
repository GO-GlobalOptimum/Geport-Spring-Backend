package go.glogprototype.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInterestTag is a Querydsl query type for InterestTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInterestTag extends EntityPathBase<InterestTag> {

    private static final long serialVersionUID = 1963959214L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInterestTag interestTag = new QInterestTag("interestTag");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public QInterestTag(String variable) {
        this(InterestTag.class, forVariable(variable), INITS);
    }

    public QInterestTag(Path<? extends InterestTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInterestTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInterestTag(PathMetadata metadata, PathInits inits) {
        this(InterestTag.class, metadata, inits);
    }

    public QInterestTag(Class<? extends InterestTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

