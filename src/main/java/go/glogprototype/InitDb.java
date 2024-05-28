//package go.glogprototype;
//
//import go.glogprototype.domain.post.domain.BookMark;
//import go.glogprototype.domain.post.domain.Post;
//import go.glogprototype.domain.user.domain.Authority;
//import go.glogprototype.domain.user.domain.Member;
//import jakarta.annotation.PostConstruct;
//import jakarta.persistence.EntityManager;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.awt.print.Book;
//import java.time.DateTimeException;
//import java.time.LocalDateTime;
//import java.util.Date;
//
//
//@Component
//@RequiredArgsConstructor
//public class InitDb {
//
//    private final InitService initService;
//
//    @PostConstruct
//    public void init() {
//
//        initService.dbInit1();
//        initService.dbInit2();
//
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService {
//
//        private final EntityManager em;
//        public void dbInit1() {
//            LocalDateTime localDateTime = LocalDateTime.now();
//            Member member = createMember("userA", "example1@gmail.com", "sss", Authority.USER,"example1@naver.com",localDateTime);
//            em.persist(member);
//
//            Post post1 = createPost("hi", 0, "spring study is good",true,0,"hihi","local/image",true,0);
//            em.persist(post1);
//            Post post2 = createPost("hi2", 0, "spring study is good2",true,0,"hihi2","local/image2",true,0);
//            em.persist(post2);
//
//            post1.setMember(member);
//            post2.setMember(member);
//
//
//        }
//
//
//
//        public void dbInit2() {
//            LocalDateTime localDateTime = LocalDateTime.now();
//            Member member = createMember("userB", "example2@gmail.com", "ss", Authority.GUEST,"example2@naver.com",localDateTime);
//            em.persist(member);
//
//            Post post1 = createPost("hi", 0, "spring study is bad",true,0,"hihi","local/image",true,0);
//            em.persist(post1);
//            Post post2 = createPost("hi2", 0, "spring study is bad2",true,0,"hihi2","local/image2",true,0);
//            em.persist(post2);
//
//            post1.setMember(member);
//            post2.setMember(member);
//
//            BookMark bookMark1 = createBookMark(member, post1);
//            BookMark bookMark2 = createBookMark(member, post2);
//            em.persist(bookMark1);
//            em.persist(bookMark2);
//
//
//        }
//
//        private Post createPost(String title,int viewCount, String postContent,boolean isPublic, int likeCount, String text, String imageUrl, boolean isComment,int commentCount){
//            Post post = new Post();
//
//            post.setTitle(title);
//            post.setViewsCount(viewCount);
//            post.setPostContent(postContent);
//            post.setPublic(isPublic);
//            post.setLikeCount(likeCount);
//            post.setThumbnailText(text);
//            post.setThumbnailImage(imageUrl);
//            post.setComment(isComment);
//            post.setCommentCount(commentCount);
//            return post;
//        }
//
//        private Member createMember(String name, String gmail, String nickName, Authority authority, String email, LocalDateTime birthDate) {
//            Member member = new Member();
//            member.setName(name);
//            member.setG_mail(gmail);
//            member.setNickName(nickName);
//            member.setAuthority(authority);
//            member.setEmail(email);
////            member.setBirthDate(birthDate);
//            return member;
//        }
//
//        private BookMark createBookMark(Member member,Post post) {
//
//            BookMark bookMark = new BookMark();
//            bookMark.setMember(member);
//            bookMark.setPost(post);
//
//            return bookMark;
//        }
//    }
//
//
//}
//
