package server.brainboost.src.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class AuthRequestDTO {
    @Getter
    @Setter
    @Schema(description = "refresh token 요청 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RefreshTokenRequestDTO {

        @NotBlank
        private String refreshToken;

    }
}
