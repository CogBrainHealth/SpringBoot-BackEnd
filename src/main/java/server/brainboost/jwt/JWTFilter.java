package server.brainboost.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import server.brainboost.auth.CustomUserDetails;
import server.brainboost.code.status.ErrorStatus;
import server.brainboost.src.user.entity.UserEntity;
import server.brainboost.utils.ResponseUtil;

import java.io.IOException;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //jwt 토큰 만료 -> 소셜 로그인 시도 ->jwt 토큰 만료.., 의 무한루프를 해결하기 위한 코드
        String uri = request.getRequestURI();
        if (uri.matches("^\\/login(?:\\/.*)?$") || uri.matches("^\\/oauth2(?:\\/.*)?$")) {
            filterChain.doFilter(request, response);
            return;
        }


        // 1) Authorization 헤더 읽기 (없으면 익명으로 통과)
        String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader == null || authHeader.isBlank()) {
            log.info("Authorization 헤더 없음 -> 익명 요청으로 처리");
            filterChain.doFilter(request, response);
            return;
        }

        // 2) Bearer 접두어 검증
        if (!authHeader.regionMatches(true, 0, BEARER_PREFIX, 0, BEARER_PREFIX.length())) {
            log.error("Authorization 헤더가 Bearer 형식이 아님: {}", authHeader);
            ResponseUtil.handleException(response, ErrorStatus.AUTHENTICATION_HEADER_ERROR);
            return;
        }

        // 3) 토큰만 추출 + 정리
        String accessToken = authHeader.substring(BEARER_PREFIX.length()).trim();

        //토큰 소멸 시간 검증
        // 4) 토큰 검증
        try {
            if (jwtUtil.isExpired(accessToken)) {
                log.warn("액세스 토큰 유효시간 만료");
                ResponseUtil.handleException(response, ErrorStatus.EXPIRED_ACCESS_TOKEN);
                return;
            }
        } catch (io.jsonwebtoken.security.SignatureException e) {
            log.error("서명 불일치(시크릿키 다름)", e);
            ResponseUtil.handleException(response, ErrorStatus.INVALID_TOKEN);
            return;
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            log.error("JWT 형식 오류", e);
            ResponseUtil.handleException(response, ErrorStatus.INVALID_TOKEN);
            return;
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            log.error("액세스 토큰 유효시간 만료", e);
            ResponseUtil.handleException(response, ErrorStatus.EXPIRED_ACCESS_TOKEN);
            return;
        }


        //이 후 과정은 액세스 토큰이 정상적으로 발급된 경우

        //토큰에서 username과 role 획득
        String username = jwtUtil.getUsername(accessToken);
        Long userId = jwtUtil.getUserId(accessToken);
        String role = jwtUtil.getRole(accessToken);

        //userEntity를 생성하여 값 set
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword("no error plz");
        userEntity.setRole(role);

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity, userId);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
