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

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //jwt 토큰 만료 -> 소셜 로그인 시도 ->jwt 토큰 만료.., 의 무한루프를 해결하기 위한 코드
        String requestUri = request.getRequestURI();
        if (requestUri.matches("^\\/login(?:\\/.*)?$")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (requestUri.matches("^\\/oauth2(?:\\/.*)?$")) {
            filterChain.doFilter(request, response);
            return;
        }


        String accessToken = null;
        try{
            //request에서 Authorization 헤더를 찾음
            accessToken = request.getHeader("Authorization");
        }catch (IllegalArgumentException e){

            log.error("Authorization header 가 올바르지 않습니다");
            // throw new AuthenticationHandler(ErrorStatus.AUTHENTICATION_HEADER_ERROR);
            // ResponseUtil.handleException(response,ErrorStatus.AUTHENTICATION_HEADER_ERROR);

            return;
        }

        //Authorization 헤더 검증
        if (accessToken == null) {

            log.info("accessToken이 존재하지 않은 request가 들어왔습니다");
            filterChain.doFilter(request, response);

            //ResponseUtil.handleException(response,ErrorStatus.AUTHENTICATION_HEADER_ERROR);
            //조건이 해당되면 메소드 종료 (필수), 다음 필터 또는 컨트롤러로 제어권을 넘김
            return;
        }

        //토큰 소멸 시간 검증
        try{
            if (jwtUtil.isExpired(accessToken)) {

                log.warn("토큰 시간이 만료되었습니다.");
                //filterChain.doFilter(request, response);

                ResponseUtil.handleException(response,ErrorStatus.EXPIRED_ACCESS_TOKEN);
                //조건이 해당되면 메소드 종료, 다음 필터 또는 컨트롤러로 제어권을 넘김
                return;
            }
        }catch (io.jsonwebtoken.security.SignatureException e){
            // 토큰 값이 다르경우
            log.error("시크릿키가 다릅니다.");
            //throw new GeneralException(ErrorStatus.INVALID_TOKEN);

            ResponseUtil.handleException(response,ErrorStatus.INVALID_TOKEN);
            return;
        }catch (io.jsonwebtoken.MalformedJwtException e){
            // 토큰 형식이 다른 경우
            log.error("토큰 형식이 다릅니다.");
            //throw new GeneralException(ErrorStatus.INVALID_TOKEN);

            ResponseUtil.handleException(response, ErrorStatus.INVALID_TOKEN);
            return;
        }catch (io.jsonwebtoken.ExpiredJwtException e){
            //System.out.println("token expired");
            log.error("access 토큰 유효시간이 만료되었습니다.");
            //throw new AuthenticationHandler(ErrorStatus.EXPIRED_ACCESS_TOKEN);

            ResponseUtil.handleException(response, ErrorStatus.EXPIRED_ACCESS_TOKEN);

            return;
        }

        //이 후 과정은 액세스 토큰이 정상적으로 발급된 경우이다.

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
