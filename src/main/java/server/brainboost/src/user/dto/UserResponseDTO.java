package server.brainboost.src.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

public class UserResponseDTO {
    @Getter
    @Setter
    @Schema(description = "내 정보보기 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfileDTO {

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

    @Getter
    @Setter
    @Schema(description = "로그인 후 응답을 담은 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponseDTO {

        private Boolean isNewUser;
        private Boolean isPremium;
        private String accessToken;
        private String refreshToken;

    }

    @Getter
    @Setter
    @Schema(description = "us DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserIdResponseDTO {

        Long userId;
    }

    @Getter
    @Setter
    @Schema(description = "us DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class checkMedicalResponseDTO {

        Boolean isMedicalTest;
    }

    @Getter
    @Setter
    @Schema(description = "us DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class checkPremiumMedicalResponseDTO {

        Boolean isPremiumMedicalTest;
    }


}
