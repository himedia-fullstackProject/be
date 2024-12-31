package com.example.dailyhub.service;


import com.example.dailyhub.data.dto.PostDTO;
import com.example.dailyhub.data.entity.Likes;
import com.example.dailyhub.data.entity.Post;
import com.example.dailyhub.data.entity.User;
import com.example.dailyhub.data.repository.LikesRepository;
import com.example.dailyhub.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    @Override
    @Transactional(readOnly = true)
    public List<PostDTO> readLikesPostsByUser(User user) {
        List<PostDTO> list = new ArrayList<>();
        for (Object likes : likesRepository.findAllLikesByUser(user)) {
            PostDTO postDTO = convertPostToDTO(likes.getPost());
            list.add(postDTO);
        }
        return list; // list<PostDTO> 반환

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
                .build();
    }

    @Override
    @Transactional
    public void changeLikes(User user, Post post) {
        boolean exists = likesRepository.existByUserAndPost(user, post);

        if (exists) {
            // 이미 좋아요가 있는 경우 -> 좋아요 삭제
            Likes likes = likesRepository.findLikesByPostAndUser(post , user)
                    .orElseThrow(() -> new LikesNotFoundException("좋아요를 찾을 수 없습니다."));

            likes.setPost(null);
            likesRepository.delete(likes);
        } else {
            // 좋아요가 없는 경우 -> 새로운 좋아요 추가
            Likes newLike = Likes.builder()
                    .user(user)
                    .post(post)
                    .count(1L)
                    .build();

            likesRepository.save(newLike);
            post.getLikes().add(newLike);
        }
    }
}