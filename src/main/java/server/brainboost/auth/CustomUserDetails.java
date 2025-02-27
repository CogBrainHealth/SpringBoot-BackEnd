package server.brainboost.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import server.brainboost.src.user.entity.UserEntity;

import java.util.ArrayList;
import java.util.Collection;

//summary//
//CustomUserDetails의 getUsername과 getPassword에 자동으로 사용자가 적은 id, password가 mapping 되어 로그인 검증을 진행함
//진행 후, 로그인이 되면 success로 로그인이 실패하면 fail로 넘어간다
//summary//
public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity;
    private final Long userId;
    private Boolean isNewUser;
    private Boolean isPremium;

    public CustomUserDetails(UserEntity userEntity, Boolean isNewUser, Boolean isPremium){
        this.userEntity = userEntity;
        this.userId = userEntity.getUserId();
        this.isNewUser = isNewUser;
        this.isPremium = isPremium;
    }
    public CustomUserDetails(UserEntity userEntity, Long userId){
        this.userEntity = userEntity;
        this.userId = userId;
    }

    // 임시 로그인
    public CustomUserDetails(UserEntity userEntity){
        this.userEntity = userEntity;
        this.userId = userEntity.getUserId();
    }

    public Long getUserId(){
        return userId;
    }
    public Boolean getIsNewUser() { return isNewUser; }
    public Boolean getIsPremium() {return isPremium;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return userEntity.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {

        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {

        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}
