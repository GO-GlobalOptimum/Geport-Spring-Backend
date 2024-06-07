//package go.glogprototype.domain.user.dao;
//
//import go.glogprototype.domain.user.domain.View;
//import go.glogprototype.domain.post.domain.Post;
//import go.glogprototype.domain.user.domain.Member;
//import org.springframework.data.jpa.repository.JpaRepository;
//import java.util.Optional;
//
//public interface ViewReadRepository extends JpaRepository<View, Long> {
//    Optional<View> findByPostAndMember(Post post, Member member);
//}
