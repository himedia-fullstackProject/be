package com.example.dailyhub.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.stream.LongStream;

@Slf4j
@Configuration
@Profile({"test"})
public class NotProd {

//  @Bean
//  CommandLineRunner initData(
//      TodoRepository todoRepository,
//      PasswordEncoder passwordEncoder,
//      MemberRepository memberRepository,
//      ProductRepository productRepository
//  ) {
//    return (args) -> {
//      log.info("init data...");
//
//      LongStream.rangeClosed(1, 10).forEach(i -> {
//        // todo data
//        Todo todo = Todo.builder()
//            .title("Title..." + i)
//            .writer("nick_" + i)
//            .complete(i % 2 == 0)
//            .dueDate(LocalDate.now().plusDays(1))
//            .build();
//        todoRepository.save(todo);
//        // product data
//        // 1. 상품 10개 추가
//        Product product = Product.builder()
//            .pname("상품" + i)
//            .price((int) (100 * i))
//            .pdesc("상품설명 " + i)
//            .stockNumber(100)
//            .build();
//
//        // 2개의 이미지 파일 추가
//        product.addImageString("IMAGE1.jpg");
//        product.addImageString("IMAGE2.jpg");
//
//        productRepository.save(product);
//
//
//      });
//
//      LongStream.rangeClosed(1, 5).forEach(i -> {
//        // member data
//        Member member = Member.builder()
//            .email("user" + i + "@aaa.com")
//            .pw(passwordEncoder.encode("1111"))
//            .nickname("nick_" + i)
//            .social(false)
//            .build();
//
//        member.addRole(MemberRole.ADMIN);
//
//        memberRepository.save(member);
//
//      });
//
//      LongStream.rangeClosed(6, 10).forEach(i -> {
//        // todo data
//        Member member = Member.builder()
//            .email("user" + i + "@aaa.com")
//            .pw(passwordEncoder.encode("1111"))
//            .nickname("nick_" + i)
//            .social(false)
//            .build();
//
//        member.addRole(MemberRole.USER);
//
//        memberRepository.save(member);
//      });
//    };
//  }

}
