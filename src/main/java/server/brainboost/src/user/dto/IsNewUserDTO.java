package server.brainboost.src.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "신규 이용자인지 알려주는 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IsNewUserDTO {
	private Boolean isNewUser;
}
