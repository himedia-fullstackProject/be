package com.example.dailyhub.domain.likes.service;


import com.example.dailyhub.domain.likes.entity.Likes;
import com.example.dailyhub.domain.likes.repository.LikesRepository;
import com.example.dailyhub.domain.post.dto.PostDTO;
import com.example.dailyhub.domain.post.entity.Post;
import com.example.dailyhub.domain.post.repository.PostRepository;
import com.example.dailyhub.domain.user.repository.UserRepository;
import com.example.dailyhub.dto.PageResponse;
import com.example.dailyhub.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LikesServiceImpl implements LikesService {

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    @Override
    public PageResponse getUserLikedListsPaged(String username, int pageNo, int pageSize,
        String sortBy) {
        log.info("getUserLikedListsPaged 시작... ");

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        Long userId = userRepository.findUserIdByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));

        Page<Post> likePage = likesRepository.findPostByUserId(userId, pageable);

        List<PostDTO> content = likePage.getContent().stream()
            .map(this::toDTO)
            .toList();

        return createPageResponse(likePage, content, pageNo, pageSize);
    }

    @Transactional
    @Override
    public boolean toggleLike(Long userId, Long postId) {
        log.info("toggleLike 시작...");

        Optional<Likes> existingLike = likesRepository.findByPostAndUser(postId, userId);

        if (existingLike.isPresent()) {
            likesRepository.delete(existingLike.get());
            return false;
        } else {
            Likes newLike = Likes.builder()
                .user(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다.")))
                .post(postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("포스트를 찾을 수 없습니다.")))
                .build();
            likesRepository.save(newLike);
            return true;
        }
    }

}