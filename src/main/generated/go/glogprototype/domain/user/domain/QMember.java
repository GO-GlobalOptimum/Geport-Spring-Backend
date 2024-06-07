package go.glogprototype.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1104999484L;

    public static final QMember member = new QMember("member1");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final EnumPath<Authority> authority = createEnum("authority", Authority.class);

    public final StringPath bio = createString("bio");

    public final StringPath city = createString("city");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> deletedDate = createDateTime("deletedDate", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final NumberPath<Integer> followerCount = createNumber("followerCount", Integer.class);

    public final NumberPath<Integer> followingCount = createNumber("followingCount", Integer.class);

    public final StringPath g_mail = createString("g_mail");

    public final StringPath gender = createString("gender");

    public final NumberPath<Long> geportId = createNumber("geportId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final StringPath location = createString("location");

    public final StringPath mbti = createString("mbti");

    public final StringPath name = createString("name");

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final StringPath person = createString("person");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath refreshToken = createString("refreshToken");

    public final StringPath socialId = createString("socialId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final ListPath<View, QView> views = this.<View, QView>createList("views", View.class, QView.class, PathInits.DIRECT2);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

