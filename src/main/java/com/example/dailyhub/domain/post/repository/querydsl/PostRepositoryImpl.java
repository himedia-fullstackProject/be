package com.example.dailyhub.domain.post.repository.querydsl;

import static com.example.dailyhub.domain.post.entity.QPost.post;
import static com.example.dailyhub.domain.tag.entity.QPostTag.postTag;
import static com.example.dailyhub.domain.tag.entity.QTag.tag;

import com.example.dailyhub.domain.post.entity.Post;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Post> searchPostsByTitle(String searchTerm, Pageable pageable) {

    List<Post> posts = queryFactory
        .select(post)
        .from(post)
        .where(post.title.containsIgnoreCase(searchTerm))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    JPAQuery<Long> countQuery = queryFactory
        .select(post.count())
        .from(post)
        .where(post.title.containsIgnoreCase(searchTerm));

    return PageableExecutionUtils.getPage(posts, pageable, countQuery::fetchOne);
  }

  @Override
  public Page<Post> searchPostsByTagName(String tagName, Pageable pageable) {
    List<Post> posts = queryFactory
        .select(post)
        .from(post)
        .join(post.postTags, postTag)
        .join(postTag.tag, tag)
        .where(tag.tagName.eq(tagName))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    JPAQuery<Long> countQuery = queryFactory
        .select(post.count())
        .from(post)
        .join(post.postTags, postTag)
        .join(postTag.tag, tag)
        .where(tag.tagName.eq(tagName));

    return PageableExecutionUtils.getPage(posts, pageable, countQuery::fetchOne);

  }

  @Override
  public Page<Post> findPostsByUserName(String userName, Pageable pageable) {
    List<Post> posts = queryFactory
        .select(post)
        .from(post)
        .where(post.user.username.eq(userName))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    JPAQuery<Long> countQuery = queryFactory
        .select(post.count())
        .from(post)
        .where(post.user.username.eq(userName));

    return PageableExecutionUtils.getPage(posts, pageable, countQuery::fetchOne);

  }

}
