package server.brainboost.src.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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
}
