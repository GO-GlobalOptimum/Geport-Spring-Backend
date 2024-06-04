package go.glogprototype.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMessageToAdmin is a Querydsl query type for MessageToAdmin
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessageToAdmin extends EntityPathBase<MessageToAdmin> {

    private static final long serialVersionUID = -1719315665L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMessageToAdmin messageToAdmin = new QMessageToAdmin("messageToAdmin");

    public final QMember admin;

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final StringPath title = createString("title");

    public QMessageToAdmin(String variable) {
        this(MessageToAdmin.class, forVariable(variable), INITS);
    }

    public QMessageToAdmin(Path<? extends MessageToAdmin> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMessageToAdmin(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMessageToAdmin(PathMetadata metadata, PathInits inits) {
        this(MessageToAdmin.class, metadata, inits);
    }

    public QMessageToAdmin(Class<? extends MessageToAdmin> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.admin = inits.isInitialized("admin") ? new QMember(forProperty("admin")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

