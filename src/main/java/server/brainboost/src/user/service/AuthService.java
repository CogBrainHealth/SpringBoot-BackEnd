package server.brainboost.src.user.service;

import java.util.Date;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.jwt.JWTUtil;
import server.brainboost.src.user.dto.RefreshTokenRequestDTO;
import server.brainboost.src.user.dto.JoinDTO;
import server.brainboost.src.user.dto.TokenResponseDTO;
import server.brainboost.src.user.entity.RefreshEntity;
import server.brainboost.src.user.entity.UserEntity;
import server.brainboost.src.user.repository.RefreshRepository;
import server.brainboost.src.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    public void signUp(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();

        String encodedPassword = bCryptPasswordEncoder.encode(password);

        UserEntity newUser = new UserEntity(username);

        userRepository.save(newUser);
    }

    public TokenResponseDTO reissue(RefreshTokenRequestDTO refreshTokenRequestDTO) {

        // get refresh token
        String refresh = refreshTokenRequestDTO.getRefreshToken();

        // 토큰 유효성 검사
        try{
            jwtUtil.isExpired(refresh);
        }catch (io.jsonwebtoken.security.SignatureException e){
           throw new BaseException(BaseResponseStatus.INVALID_TOKEN);
        }catch (io.jsonwebtoken.MalformedJwtException e){
            throw new BaseException(BaseResponseStatus.INVALID_TOKEN);
        }catch (io.jsonwebtoken.ExpiredJwtException e){
            throw new BaseException(BaseResponseStatus.EXPIRED_REFRESH_TOKEN);
        }

        // 토큰이 refresh인지 검사
        String category = jwtUtil.getCategory(refresh);

        if(!category.equals("refresh")){
            throw new BaseException(BaseResponseStatus.INVALID_TOKEN);
        }

        // DB에 refresh token이 있는지 검사
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if(!isExist){
            throw new BaseException(BaseResponseStatus.INVALID_TOKEN);
        }

        String username = jwtUtil.getUsername(refresh);
        Long userId = jwtUtil.getUserId(refresh);
        String role = jwtUtil.getRole(refresh);

        String newAccess = jwtUtil.createJwt("access", username,userId, role, 7*24*60*60*1000L);

        String newRefresh = jwtUtil.createJwt("refresh", username, userId, role, 30*24*60*60*1000L);

        // DB에 기존 refresh 토큰 삭제 후, 새 refresh 토큰 저장
        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(username, newRefresh, 30*24*60*60*1000L);

        return new TokenResponseDTO(newAccess, newRefresh);


    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }

    public void logout(RefreshTokenRequestDTO refreshTokenRequestDTO) {

        // get refresh token
        String refresh = refreshTokenRequestDTO.getRefreshToken();

        // 토큰 유효성 검사
        try{
            jwtUtil.isExpired(refresh);
        }catch (io.jsonwebtoken.security.SignatureException e){
            throw new BaseException(BaseResponseStatus.INVALID_TOKEN);
        }catch (io.jsonwebtoken.MalformedJwtException e){
            throw new BaseException(BaseResponseStatus.INVALID_TOKEN);
        }catch (io.jsonwebtoken.ExpiredJwtException e){
            throw new BaseException(BaseResponseStatus.EXPIRED_REFRESH_TOKEN);
        }

        // 토큰이 refresh인지 검사
        String category = jwtUtil.getCategory(refresh);

        if(!category.equals("refresh")){
            throw new BaseException(BaseResponseStatus.INVALID_TOKEN);
        }

        // DB에 refresh token이 있는지 검사
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if(!isExist){
            throw new BaseException(BaseResponseStatus.INVALID_TOKEN);
        }

        // 로그아웃 진행
        refreshRepository.deleteByRefresh(refresh);


    }
}
