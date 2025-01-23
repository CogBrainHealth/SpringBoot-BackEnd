package server.brainboost.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import server.brainboost.auth.CustomUserDetails;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.src.user.entity.UserEntity;
import server.brainboost.utils.ResponseUtil;

import java.io.IOException;

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
            ResponseUtil.handleException(response,HttpServletResponse.SC_INTERNAL_SERVER_ERROR ,new BaseException(BaseResponseStatus.HEADER_ERROR));
        }

        //Authorization 헤더 검증
        if (accessToken == null) {

            System.out.println("Authorization이 비어있습니다");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        System.out.println("authorization now");

        //토큰 소멸 시간 검증
        try{
            if (jwtUtil.isExpired(accessToken)) {

                System.out.println("token expired");
                filterChain.doFilter(request, response);

                //조건이 해당되면 메소드 종료 (필수)
                return;
            }
        }catch (io.jsonwebtoken.security.SignatureException e){
            ResponseUtil.handleException(response,HttpServletResponse.SC_UNAUTHORIZED ,new BaseException(BaseResponseStatus.INVALID_TOKEN));
            return;
        }catch (io.jsonwebtoken.MalformedJwtException e){
            ResponseUtil.handleException(response, HttpServletResponse.SC_UNAUTHORIZED, new BaseException(BaseResponseStatus.INVALID_TOKEN));
            return;
        }catch (io.jsonwebtoken.ExpiredJwtException e){
            System.out.println("token expired");
            //filterChain.doFilter(request, response);
            ResponseUtil.handleException(response, HttpServletResponse.SC_UNAUTHORIZED ,new BaseException(BaseResponseStatus.EXPIRED_ACCESS_TOKEN));
            //조건이 해당되면 메소드 종료 (필수)
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
