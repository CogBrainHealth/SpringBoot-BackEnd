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

    @Schema(description = "유저 아이디")
    private Long userId;

    @Schema(description = "유저 닉네임")
    private String nickname;

    //enum 처리
    @Schema(description = "유저 성별")
    private Character gender;

    @Schema(description = "유저 생년월일")
    private LocalDate birthDate;

    //프로필 이미지
    @Schema(description = "유저 프로필 이미지")
    private String profileImgUrl;

    //user 권한 ROLE_ADMIN, ROLE_USER로 구분
    @Schema(description = "유저 권한")
    private String role;


}
