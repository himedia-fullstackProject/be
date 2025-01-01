package com.example.dailyhub.service;


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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    @Transactional(readOnly = true)
    public Page<PostDTO> readLikesPostsByUser(User user, Pageable pageable) {
        // 사용자가 좋아요 한 게시물
        Page<Likes> likesPage = likesRepository.findAllByUser(user, pageable);

        // Page<Likes> -> Page<PostDTO>
        return likesPage.map(likes -> convertPostToDTO(likes.getPost()));
    }

    //post -> post dto
    private PostDTO convertPostToDTO(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .tag1(post.getTag1())
                .tag2(post.getTag2())
                .tag3(post.getTag3())
                .mainCategoryId(post.getMainCategory().getId())
                .subCategoryId(post.getSubCategory().getId())
                .username(post.getUser().getUsername()) // 작성자 정보 추가
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }


    @Override
    public void changeLikes(User user , Post post) {
        boolean exists = likesRepository.existsByUserAndPost(user, post);
               //존재하면 true , 존재하지 않으면 false
        if (exists) {
            Likes likes = likesRepository.findByPostAndUser(post, user)
                    .orElseThrow(() -> new IllegalArgumentException("좋아요를 찾을 수 없습니다."));

            likesRepository.delete(likes);
        } else {
            Likes newLike = Likes.builder()
                    .user(user)
                    .post(post)
                    .count(1L)
                    .build();

            likesRepository.save(newLike);

        }
    }


}