package server.brainboost.src.user.service;

import java.util.Date;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import server.brainboost.code.status.ErrorStatus;
import server.brainboost.exception.GeneralException;
import server.brainboost.jwt.JWTUtil;
import server.brainboost.src.user.dto.AuthRequestDTO;
import server.brainboost.src.user.dto.AuthResponseDTO;
import server.brainboost.src.user.dto.UserRequestDTO;
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
    public void signUp(UserRequestDTO.JoinRequestDTO joinRequestDTO) {

        String username = joinRequestDTO.getUsername();
        String password = joinRequestDTO.getPassword();

        String encodedPassword = bCryptPasswordEncoder.encode(password);

        UserEntity newUser = new UserEntity(username);

        userRepository.save(newUser);
    }

    public AuthResponseDTO.TokenResponseDTO reissue(AuthRequestDTO.RefreshTokenRequestDTO refreshTokenRequestDTO) {

        // get refresh token
        String refresh = refreshTokenRequestDTO.getRefreshToken();

        // 토큰 유효성 검사
        try{
            jwtUtil.isExpired(refresh);
        }catch (io.jsonwebtoken.security.SignatureException e){
            throw new GeneralException(ErrorStatus.INVALID_TOKEN);
        }catch (io.jsonwebtoken.MalformedJwtException e){
            throw new GeneralException(ErrorStatus.INVALID_TOKEN);
        }catch (io.jsonwebtoken.ExpiredJwtException e){
            throw new GeneralException(ErrorStatus.INVALID_TOKEN);
        }

        // 토큰이 refresh인지 검사
        String category = jwtUtil.getCategory(refresh);

        if(!category.equals("refresh")){
            throw new GeneralException(ErrorStatus.INVALID_TOKEN);
        }

        // DB에 refresh token이 있는지 검사
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if(!isExist){
            throw new GeneralException(ErrorStatus.INVALID_TOKEN);
        }

        String username = jwtUtil.getUsername(refresh);
        Long userId = jwtUtil.getUserId(refresh);
        String role = jwtUtil.getRole(refresh);

        String newAccess = jwtUtil.createJwt("access", username,userId, role, 7*24*60*60*1000L);

        String newRefresh = jwtUtil.createJwt("refresh", username, userId, role, 30*24*60*60*1000L);

        // DB에 기존 refresh 토큰 삭제 후, 새 refresh 토큰 저장
        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(username, newRefresh, 30*24*60*60*1000L);

        return new AuthResponseDTO.TokenResponseDTO(newAccess, newRefresh);


    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }

    public void logout(AuthRequestDTO.RefreshTokenRequestDTO refreshTokenRequestDTO) {

        // get refresh token
        String refresh = refreshTokenRequestDTO.getRefreshToken();

        // 토큰 유효성 검사
        try{
            jwtUtil.isExpired(refresh);
        }catch (io.jsonwebtoken.security.SignatureException e){
            throw new GeneralException(ErrorStatus.INVALID_TOKEN);
        }catch (io.jsonwebtoken.MalformedJwtException e){
            throw new GeneralException(ErrorStatus.INVALID_TOKEN);
        }catch (io.jsonwebtoken.ExpiredJwtException e){
            throw new GeneralException(ErrorStatus.EXPIRED_REFRESH_TOKEN);
        }

        // 토큰이 refresh인지 검사
        String category = jwtUtil.getCategory(refresh);

        if(!category.equals("refresh")){
            throw new GeneralException(ErrorStatus.INVALID_TOKEN);
        }

        // DB에 refresh token이 있는지 검사
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if(!isExist){
            throw new GeneralException(ErrorStatus.INVALID_TOKEN);

        }

        // 로그아웃 진행
        refreshRepository.deleteByRefresh(refresh);


    }
}
