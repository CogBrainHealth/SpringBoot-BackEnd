package server.brainboost.src.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "내 정보보기 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {

    private Long userId;

    private String nickname;

    //enum 처리
    private Character gender;

    private LocalDate birthDate;

    //프로필 이미지
    private String profileImgUrl;

    //user 권한 ROLE_ADMIN, ROLE_USER로 구분
    private String role;


}
