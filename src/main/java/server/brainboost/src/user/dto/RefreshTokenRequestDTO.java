package server.brainboost.src.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "refresh token 요청 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequestDTO {

	@NotBlank
	private String refreshToken;

}
