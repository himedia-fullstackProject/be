package com.example.dailyhub.config;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

//  private final AuthenticationConfiguration authenticationConfiguration;
//  private final JwtUtil jwtUtil;
//  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
//  private final CustomAccessDeniedHandler customAccessDeniedHandler;
//  private final CustomOAuth2UserService customOauth2UserService;
//  private final CustomSuccessHandler customSuccessHandler;

  @Bean
  public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public LogoutSuccessHandler logoutHandler() {
    return (request, response, authentication) -> {
      response.setStatus(HttpStatus.OK.value());
      response.getWriter().write("logout success");
    };
  }

  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .formLogin(formLogin -> formLogin.disable())
        .httpBasic(httpBasic -> httpBasic.disable())
        .authorizeHttpRequests(authorize ->
            authorize.requestMatchers("/")
                .permitAll()
                .requestMatchers("/").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
        );

    http.cors(cors -> cors.configurationSource(request -> {
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials(true);
      config.setAllowedOrigins(
          Arrays.asList("http://localhost:3000", "http://localhost:3001",
              "http://localhost:3002")
      );
      config.addAllowedHeader("*");
      config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
      config.addExposedHeader("Authorization");
      return config;
    }));

    http.logout(logout ->
        logout.logoutUrl("/logout")
            .logoutSuccessHandler(logoutHandler())
            .addLogoutHandler((request, response, authentication) -> {
              if (request.getSession() != null) {
                request.getSession().invalidate();
              }
            })
            .deleteCookies("JSESSIONID", "Authorization", "access", "refresh", "token")
    );

    http.sessionManagement(session ->
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

//    http.addFilterBefore(new JwtFilter(this.jwtUtil), LoginFilter.class);
//
//    http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
//        UsernamePasswordAuthenticationFilter.class);
//
//    http.oauth2Login(oauth2 -> oauth2
//        .userInfoEndpoint(
//            userInfo -> userInfo.userService(customOauth2UserService)) // 사용자 정보 서비스 설정
//        .successHandler(customSuccessHandler) // 로그인 성공 시 핸들러
//        .failureHandler((request, response, exception) -> { // 로그인 실패 시 핸들러
//          response.setStatus(HttpStatus.UNAUTHORIZED.value());
//          response.getWriter().write("OAuth2 Login Failed");
//        })
//    );
//
//    http.exceptionHandling(exception -> {
//          exception.authenticationEntryPoint(customAuthenticationEntryPoint);
//          exception.accessDeniedHandler(customAccessDeniedHandler);
//        }
//    );

    return http.build();
  }

}