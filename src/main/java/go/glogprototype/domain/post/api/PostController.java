package go.glogprototype.domain.post.api;

import go.glogprototype.domain.post.application.PostService;
import go.glogprototype.domain.post.dao.PostRepository;
import go.glogprototype.domain.post.dto.PostDto;
import go.glogprototype.domain.post.dto.PostDto.*;
import go.glogprototype.domain.post.dto.PostWriteDto;
import go.glogprototype.domain.user.dao.MemberRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("post")
public class PostController {



    private final PostService postService;

    //리스트
    @GetMapping("/list")
    public Page<FindPostResponseDto> postList(@RequestParam(required = false) String keyword, Pageable pageable){
        log.info("test={}", keyword);
        return postService.findAllPost(keyword, pageable);
    }

    @PostMapping("/write")
    public ResponseEntity<PostWriteDto> postWrite(@RequestBody PostWriteDto postWriteDto){
        return postService.saveAllPost(postWriteDto);
    }


}
