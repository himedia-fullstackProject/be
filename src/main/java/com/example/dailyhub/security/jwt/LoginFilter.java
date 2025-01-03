package com.example.dailyhub.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    String username = obtainUsername(request);
    String password = obtainPassword(request);

    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
        username, password, null);

    return authenticationManager.authenticate(authRequest);
  }

  @Override
  public void successfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException {
    CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

    String userId = String.valueOf(userDetails.getId());
    String username = userDetails.getUsername();
    String role = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining());

    LocalDate joinDate = userDetails.getJoinDate();

    /**
     * accessToken과 refresh 쿠키를 생성
     */
    String accessToken = this.jwtUtil.CreateJWT("access", username, role, 60*60*1000L);
    String refreshToken = this.jwtUtil.CreateJWT("refresh", username, role, 24*60*60*1000L);

    Map<String, Object> responseData = new HashMap<>();
    responseData.put("user_id", userId);
    responseData.put("username", username);
    responseData.put("nickname", userDetails.getNickname());
    responseData.put("phone_number", userDetails.getPhoneNumber());
    responseData.put("joinDate", joinDate);
    responseData.put("role", role);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    String jsonMessage = objectMapper.writeValueAsString(responseData);

    response.addHeader("Authorization", "Bearer " + accessToken);
    response.addCookie(createCookie("refresh",refreshToken));
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    response.getWriter().write(jsonMessage);
  }

  /**
   * createCookie 만드는 메서드
   * cookie 형태 = (key, value)
   */
  private Cookie createCookie(String key, String value) {
    Cookie cookie = new Cookie(key, value);
    cookie.setPath("/"); // 모든 경로에서 발생
    cookie.setHttpOnly(true); // 자바스크립트 접근 불가
    cookie.setMaxAge(24*60*60);
    return cookie;
  }

  @Override
  public void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res,
      AuthenticationException failed) throws IOException, ServletException {
    Map<String, String> responseData = new HashMap<>();
    responseData.put("message", "계정정보가 틀립니다.");

    ObjectMapper objectMapper = new ObjectMapper();
    String jsonmessage = objectMapper.writeValueAsString(responseData);

    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");
    res.getWriter().write(jsonmessage);
  }
}
