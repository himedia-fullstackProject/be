package com.example.dailyhub.domain.tag.util;

import com.example.dailyhub.domain.post.entity.Post;
import com.example.dailyhub.domain.tag.entity.PostTag;
import com.example.dailyhub.domain.tag.entity.Tag;
import com.example.dailyhub.domain.tag.repository.TagRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagUtil {

  private final TagRepository tagRepository;

  public List<PostTag> mapTagsToPost(Post post, List<String> tags) {
    return tags.stream()
        .map(tagName -> {
          Tag tag = tagRepository.findByTagName(tagName)
              .orElseGet(() -> Tag.builder().tagName(tagName).count(0).build());

          tag.setCount(tag.getCount() + 1);
          tagRepository.save(tag);

          return PostTag.builder()
              .post(post)
              .tag(tag)
              .build();
        }).toList();
  }
}
