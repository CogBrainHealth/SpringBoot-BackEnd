package server.brainboost.src.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "기본정보 작성 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicInfoDTO {

    @Schema(description = "닉네임", nullable = false, example = "눈사람")
    @NotBlank
    private String nickname;

    @Schema(description = "성별", nullable = false, example = "M", allowableValues = {"M", "W"})
    @NotNull
    private Character gender;

    @Schema(description = "나이", nullable = false, example = "2001-12-13")
    private LocalDate birthDate;

}
