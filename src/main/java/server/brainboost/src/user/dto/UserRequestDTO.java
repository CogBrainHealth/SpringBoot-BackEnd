package server.brainboost.src.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

public class UserRequestDTO {

    @Getter
    @Schema(description = "Object Mapper을 통해 json 받아올 DTO")
    public static class LoginRequestDTO {

        private String username;
        private String password;

    }

    @Getter
    @Schema(description = "회원가입 요청 DTO")
    public static class JoinRequestDTO {

        @NotBlank(message = "username은 필수 입력값입니다.")
        private String username;

        @NotBlank(message = "password는 필수 입력값입니다.")
        private String password;

        @Schema(description = "유저 닉네임", example = "johndoe")
        @NotBlank(message = "닉네임을 입력해주세요")
        private String nickname;

        @Schema(description = "성별", nullable = false,
                example = "M", allowableValues = {"M", "W"})
        @NotNull(message = "성별을 선택해주세요")
        private Character gender;

        @Schema(description = "유저 생년월일",
                example = "1990-01-01")
        @NotNull(message = "생년월일을 입력해주세요")
        private LocalDate birthDate;

        @Schema(description = "개인정보 동의여부")
        @NotNull(message = "개인정보 동의여부를 입력해주세요")
        private Boolean isPrivacy;
    }

    @Getter
    public static class NicknameRequestDTO {
        @Schema(description = "유저 닉네임", example = "johndoe")
        @NotBlank(message = "닉네임을 입력해주세요")
        private String nickname;
    }

    @Getter
    public static class GenderRequestDTO {
        @Schema(description = "성별", nullable = false,
                example = "M", allowableValues = {"M", "W"})
        @NotNull(message = "성별을 선택해주세요")
        private Character gender;
    }

    @Getter
    public static class BirthDateRequestDTO {
        @Schema(description = "유저 생년월일",
                example = "1990-01-01")
        @NotNull(message = "생년월일을 입력해주세요")
        private LocalDate birthDate;
    }
}
