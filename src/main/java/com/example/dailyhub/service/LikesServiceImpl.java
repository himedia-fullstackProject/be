package com.example.dailyhub.service;


import com.example.dailyhub.data.dto.PageResponse;
import com.example.dailyhub.data.dto.PostDTO;
import com.example.dailyhub.data.entity.Likes;
import com.example.dailyhub.data.entity.Post;
import com.example.dailyhub.data.entity.User;
import com.example.dailyhub.data.repository.LikesRepository;
import com.example.dailyhub.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LikesServiceImpl implements LikesService {

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;

//좋아요 누른 포스트 상세보기
//    @Override
//    @Transactional(readOnly = true)
//    public List<PostDTO> readLikesPostsByUser(User user) {
//        List<PostDTO> list = new ArrayList<>();
//        for (Likes likes : likesRepository.findAllByUser(user)) {
//            Post post = likes.getPost();
//            PostDTO postDTO = convertPostToDTO(post);
//            list.add(postDTO);
//        }
//        return list; // 좋아요를 누른 list<PostDTO> 반환
//
//    }


    //aggregator, facade 패턴 공부
    // @Transactional 공부
    // 구현 해서 타 서비스 조회 부분 분리
    @Override
    @Transactional(readOnly = true)
    public PageResponse<PostDTO> readLikesPostsByUser(final Long userId, final Pageable pageable) {
        User user = userRepository.findById(userId).orElse(null);
        // 얘도 서비스
        Page<Likes> likesPage = likesRepository.findByUser(user,pageable);


        // DTO 변환은 서비스 마지막에서 구현 컨트롤러는 서비스 통신 및
        // Page<Likes>를 Page<PostDTO>로 변환
        Page<PostDTO> postDTOPage = likesPage.map(likes -> convertPostToDTO(likes.getPost()));

        // Page<PostDTO>를 PageResponse<PostDTO>로 변환
        return new PageResponse<>(postDTOPage);
    }

    //post -> post dto
    private PostDTO convertPostToDTO(Post post) {
        String username = userRepository.findByUserId(post.getUser().getId())
                .orElseThrow(() -> new RuntimeException("유저 닉네임 조회 실패"));
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .tag1(post.getTag1())
                .tag2(post.getTag2())
                .tag3(post.getTag3())
                .mainCategoryId(post.getMainCategory().getId())
                .subCategoryId(post.getSubCategory().getId())
                .userId(post.getUser().getId()) // 작성자 정보 추가
                .username(username)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    @Override
    public boolean changeLikes(User user, Post post) {
        boolean exists = likesRepository.existsByUserAndPost(user, post);
        //얼리 리턴 하세요.
        //존재하면 true , 존재하지 않으면 false
        if (exists) {
            likesRepository.findByPostAndUser(post, user)
                    .ifPresent(likes -> {
                        likesRepository.delete(likes);
                    });

            return false;
        } else {
            Likes newLike = Likes.builder()
                    .user(user)
                    .post(post)
                    .build();

            likesRepository.save(newLike);
            return true;
        }
    }


}