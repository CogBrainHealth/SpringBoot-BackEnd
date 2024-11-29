package server.brainboost.src.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "user")
@NoArgsConstructor
@DynamicInsert
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String username;

    //비밀번호 글자 수 제한
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 10)
    private String nickname;

    //enum 처리
    @Column(nullable = false)
    private Character gender;

    @Column(name = "birth_date", nullable = false)
    private String birthDate;

    //프로필 이미지
    @Column(nullable = true)
    private String profileImgUrl;

    //user 권한 ROLE_ADMIN, ROLE_USER로 구분
    @Column
    private String role;

    /**비즈니스 로직**/
    //임시 회원가입을 위한 생성자
    public UserEntity(String username, String password){

        this.username = username;
        this.password = password;
        this.nickname = username;
        this.gender = 'M';
        this.birthDate = "20000101";
        this.role = "ROLE_USER";

    }


}
