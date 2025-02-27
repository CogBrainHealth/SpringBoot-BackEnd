package server.brainboost.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.config.Status;
import server.brainboost.src.user.entity.UserEntity;
import server.brainboost.src.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /*UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElse(null);

        Boolean isNewUser;

        //신규 유저
        if(userEntity == null){
            userEntity = new UserEntity(username);
            userEntity.setPassword(bCryptPasswordEncoder.encode("1234"));
            userEntity.setIsPremium(Boolean.FALSE);
            userRepository.save(userEntity);
            isNewUser = Boolean.TRUE;
        }else{
            isNewUser = Boolean.FALSE;
        }

        return new CustomUserDetails(userEntity, isNewUser, userEntity.getIsPremium());*/


        // 임시 로그인
        UserEntity user = userRepository.findUserEntityByUsername(username)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NO_EXIST));

        System.out.println("현재 userId는 " + user.getUserId());
        return new CustomUserDetails(user, Boolean.FALSE, user.getIsPremium());

    }
}
