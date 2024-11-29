package server.brainboost.src.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Schema(description = "회원가입 요청 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {

    private String username;
    private String password;
}
