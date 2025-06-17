package server.brainboost.src.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;
import server.brainboost.src.medical.entity.MedicalChecklistEntity;
import server.brainboost.src.user.dto.UserRequestDTO;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "user")
@NoArgsConstructor
@DynamicInsert
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;

    @Column(nullable = false)
    private String username;

    //비밀번호 글자 수 제한
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    //enum 처리
    @Column(nullable = false)
    private Character gender;

    @Column(name = "birth_date", nullable = true)
    private LocalDate birthDate;

    //프로필 이미지
    @Column(nullable = true)
    private String profileImgUrl;

    //user 권한 ROLE_ADMIN, ROLE_USER로 구분
    @Column
    private String role;

    @Column(name = "is_premium", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isPremium;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_checklist_id") // 외래 키 설정
    private MedicalChecklistEntity medicalChecklist;


    /**비즈니스 로직**/
    //임시 회원가입을 위한 생성자
   /* public UserEntity(String username, String password){

        this.username = username;
        this.password = password;
        this.nickname = username;
        this.gender = 'M';
        this.birthDate = null;
        this.role = "ROLE_USER";
    }*/

    public UserEntity(String username){
        this.username = username;
        this.nickname = username;
        this.gender = 'W';
        this.role = "ROLE_USER";
    }

    public UserEntity(UserRequestDTO.JoinDTO joinDTO){
        this.username = joinDTO.getUsername();
        this.nickname = joinDTO.getNickname();
        this.gender = joinDTO.getGender();
        this.birthDate = joinDTO.getBirthDate();
        this.role = "ROLE_USER";
    }


}
