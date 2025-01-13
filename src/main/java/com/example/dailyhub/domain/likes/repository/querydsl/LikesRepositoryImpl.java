package com.example.dailyhub.domain.likes.repository.querydsl;

import static com.example.dailyhub.domain.likes.entity.QLikes.likes;

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
public class LikesRepositoryImpl implements LikesRepositoryCustom{

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Post> findPostByUserId(Long userId, Pageable pageable) {

    List<Post> posts = queryFactory
        .select(likes.post)
        .from(likes)
        .where(likes.user.id.eq(userId))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    JPAQuery<Long> countQuery = queryFactory
        .select(likes.post.count())
        .from(likes)
        .where(likes.user.id.eq(userId));

    return PageableExecutionUtils.getPage(posts, pageable, countQuery::fetchOne);
  }
}
// ToDo 현재 tags, userNickname,image null 값 나오는데 이거 해결하기