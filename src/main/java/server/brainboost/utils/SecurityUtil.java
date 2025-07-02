package server.brainboost.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import server.brainboost.auth.CustomUserDetails;
import server.brainboost.code.status.ErrorStatus;
import server.brainboost.exception.BaseException;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.exception.GeneralException;
import server.brainboost.exception.handler.AuthenticationHandler;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

@Slf4j
public class SecurityUtil {

    //userId 가져오기
    public static Optional<Long> getCurrentUserId(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails customUserDetails;

        try {
            customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        }catch (ClassCastException e){
            log.error("accessToken을 입력해야 합니다.");
            throw  new GeneralException(ErrorStatus._UNAUTHORIZED);
        }catch (Exception e){
            throw new AuthenticationHandler(ErrorStatus.USER_NO_EXIST);
        }

        if(customUserDetails.getUserId()==null){
            log.error("userId가 null 입니다.");
            throw new GeneralException(ErrorStatus.USER_NO_EXIST);
        }
        return Optional.of(customUserDetails.getUserId());

    }

    //role 가져오기
    public static String getCurrentUserRole(){

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();

        return auth.getAuthority();


    }


}
