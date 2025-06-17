package server.brainboost.src.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "회원가입 요청 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinDTO {

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
}
