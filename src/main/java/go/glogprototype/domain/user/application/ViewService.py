package go.glogprototype.domain.user.application;

import go.glogprototype.domain.user.domain.View;
import go.glogprototype.domain.post.domain.Post;
import go.glogprototype.domain.user.domain.Member;
import go.glogprototype.domain.user.dao.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ViewService {
    @Autowired
    private ViewRepository viewRepository;

    @Transactional
    public void addView(Member member, Post post) {
        View view = viewRepository.findByPostAndMember(post, member)
                .orElseGet(() -> new View(post, member));

        view.incrementViewCount();
        viewRepository.save(view);
    }
}
