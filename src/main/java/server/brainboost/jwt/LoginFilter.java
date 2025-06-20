package server.brainboost.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import server.brainboost.auth.CustomUserDetails;
import server.brainboost.exception.BaseException;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.src.user.dto.UserRequestDTO;
import server.brainboost.src.user.dto.UserResponseDTO;
import server.brainboost.src.user.entity.RefreshEntity;
import server.brainboost.src.user.repository.RefreshRepository;
import server.brainboost.utils.ResponseUtil;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //클라이언트 요청에서 username, password 추출
        //String username = obtainUsername(request);
        //String password = obtainPassword(request);

        //ObjectMapper 이용 시, json 형식으로 로그인 요청을 받을 수 있다.(username, password)
        ObjectMapper om = new ObjectMapper();
        UserRequestDTO.LoginRequestDTO loginRequestDTO = null;

        try {
            loginRequestDTO = om.readValue(request.getInputStream(), UserRequestDTO.LoginRequestDTO.class);
        } catch (IOException e) {
            //throw new RuntimeException(e);

            ResponseUtil.handleException(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR ,new BaseException(BaseResponseStatus.NO_VALID_LOGINDTO));
            return null;
        }

        if(loginRequestDTO == null){
            ResponseUtil.handleException(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR ,new BaseException(
                BaseResponseStatus.NO_VALID_LOGINDTO));

            return null;
        }

        if(loginRequestDTO.getUsername() == null || loginRequestDTO.getUsername().isBlank()){
            ResponseUtil.handleException(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR ,new BaseException(
                BaseResponseStatus.NO_VALID_LOGINDTO));

            return null;
        }

        if(loginRequestDTO.getPassword() == null || loginRequestDTO.getPassword().isBlank()){
            ResponseUtil.handleException(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR ,new BaseException(
                BaseResponseStatus.NO_VALID_LOGINDTO));

            return null;
        }

        String username = loginRequestDTO.getUsername();
        String password = loginRequestDTO.getPassword();

        System.out.println(username + " " + password);
        //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        //token에 담은 검증을 위한 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException{
        System.out.println("success");

        //UserDetailsS
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();
        Long userId = customUserDetails.getUserId();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        // 토큰 생성
        // access token : 7일
        String accessToken = jwtUtil.createJwt("access", username, userId, role, 7*24*60*60*1000L);
        // refresh token : 30일
        String refreshToken = jwtUtil.createJwt("refresh", username, userId, role, 30*24*60*60*1000L);

        //refresh Toekn 저장
        addRefreshEntity(username, refreshToken, 30*24*60*60*1000L);

        // HTTP 인증방식은 RFC7325 정의에 따라 Authorization: Bearer + 인증 토큰 형식으로 전달 되어야 함
        // response.addHeader("Authorization", "Bearer " + token);


        // 응답 상태 코드 및 본문 설정
        ResponseUtil.handleResponse(response, new UserResponseDTO.LoginResponseDTO(customUserDetails.getIsNewUser(), customUserDetails.getIsPremium(), accessToken, refreshToken));
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        System.out.println("fail");
        ResponseUtil.handleException(response,HttpServletResponse.SC_UNAUTHORIZED ,new BaseException(BaseResponseStatus.FAILED_LOGIN));
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }
}