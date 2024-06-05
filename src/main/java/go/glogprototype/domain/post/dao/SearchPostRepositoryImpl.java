package go.glogprototype.domain.post.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import go.glogprototype.domain.post.domain.Category;
import go.glogprototype.domain.post.domain.Post;
import go.glogprototype.domain.post.domain.QBookMark;
import go.glogprototype.domain.post.dto.PostDto.*;
import go.glogprototype.domain.user.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;


import java.util.List;


import static go.glogprototype.domain.post.domain.QCategory.category;
import static go.glogprototype.domain.post.domain.QCategoryPost.categoryPost;
import static go.glogprototype.domain.post.domain.QPost.post;
import static go.glogprototype.domain.user.domain.QMember.member;
import static go.glogprototype.domain.post.domain.QBookMark.bookMark;
import static org.hibernate.internal.util.collections.ArrayHelper.forEach;


@Repository
public class SearchPostRepositoryImpl extends QuerydslRepositorySupport implements SearchPostRepository{

    private final JPAQueryFactory jpaQueryFactory;
    public SearchPostRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Post.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }
    @Override
    public Page<FindPostResponseDto> postListResponseDto(String keyword, Pageable pageable, Long memberId) {

        List<FindPostResponseDto> findPostResponseDtos = jpaQueryFactory
                .select(Projections.fields(FindPostResponseDto.class,
                                post.id, post.title, post.postContent, member.name, post.createdAt, post.thumbnailImage, post.likeCount, post.commentCount,

                                ExpressionUtils.as(
                                        JPAExpressions.select(new CaseBuilder()
                                                        .when(Expressions.asBoolean(bookMark.id==null).isTrue()).then(false)
                                                        .otherwise(true)
                                                        .as("bookMark"))
                                                .from(bookMark)
                                                .where(bookMark.post.eq(post),bookMark.member.eq(member))
                                        ,
                                        "bookMark")
                        )
                )
                .from(post)
                .innerJoin(post.member,member)
                .where(containKeywordAndMemberId(keyword,memberId),post.isDelete.eq(false))
//                  .where(containKeywordAnd)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        post.createdAt.asc()
                ).fetch();




        return new PageImpl<>(findPostResponseDtos, pageable, findPostResponseDtos.size());

    }

//    @Override
//    public Page<FindPostResponseDto> postListByCategory(Long categoryId,Pageable pageable) {
//
//        List<Long> postIds = jpaQueryFactory.select(categoryPost.post.id)
//                .from(category)
//                .innerJoin(category.categoryPostList, categoryPost)
//                .where(category.id.eq(categoryId))
//                .fetch( );
//
//        List<FindPostResponseDto> findPostResponseDtos = jpaQueryFactory
//                .select(Projections.fields(FindPostResponseDto.class,
//                                post.id, post.title, post.postContent, member.name, post.createdAt, post.thumbnailImage, post.likeCount, post.commentCount,
//
//                                ExpressionUtils.as(
//                                        JPAExpressions.select(new CaseBuilder()
//                                                        .when(Expressions.asBoolean(bookMark.id==null).isTrue()).then(false)
//                                                        .otherwise(true)
//                                                        .as("bookMark"))
//                                                .from(bookMark)
//                                                .where(bookMark.post.eq(post),bookMark.member.eq(member))
//                                        ,
//                                        "bookMark")
//                        )
//                )
//                .from(post)
//                .innerJoin(post.member,member)
//                .where(post.id.in(postIds),post.isDelete.eq(false))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .orderBy(
//                        post.createdAt.asc()
//                ).fetch();
//
//
//        return new PageImpl<>(findPostResponseDtos, pageable, findPostResponseDtos.size());
//
//    }



//    BooleanExpression containKeyword(String keyword){
//        if(keyword == null || keyword.isEmpty())
//            return null;
//        return post.postContent.contains(keyword).or(post.title.contains(keyword));
//    }

    BooleanExpression containKeywordAndMemberId(String keyword, Long memberId){

        if( (memberId==null) && (keyword ==null || keyword.isEmpty())) {
            return null;
        }
        else if((memberId==null)&&!(keyword==null || keyword.isEmpty())) {
            return post.postContent.contains(keyword).or(post.title.contains(keyword));
        }

        else if((memberId!=null)&&(keyword == null || keyword.isEmpty())) {
            return post.member.id.eq(memberId);
        }

        else {
            return post.postContent.contains(keyword).or(post.title.contains(keyword)).or(post.member.id.eq(memberId));
        }



    }

//    BooleanExpression containTitle(String keyword){
//        if(keyword == null || keyword.isEmpty())
//            return null;
//        System.out.println("keyword = " + keyword);
//        return post.title.contains(keyword);
//    }

}