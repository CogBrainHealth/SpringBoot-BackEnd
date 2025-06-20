package server.brainboost.src.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class AuthResponseDTO {
    @Getter
    @Setter
    @Schema(description = "access token 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TokenResponseDTO {

        private String accessToken;
        private String refreshToken;

    }
}
