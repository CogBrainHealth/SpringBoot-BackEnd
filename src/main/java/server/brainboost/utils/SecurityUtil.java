package server.brainboost.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import server.brainboost.auth.CustomUserDetails;
import server.brainboost.exception.BaseException;
import server.brainboost.base.BaseResponseStatus;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

public class SecurityUtil {

    //userId 가져오기
    public static Optional<Long> getCurrentUserId(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails customUserDetails;

        try {
            customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        }catch (ClassCastException e){
            return Optional.empty();
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.UNEXPECTED_ERROR);
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
