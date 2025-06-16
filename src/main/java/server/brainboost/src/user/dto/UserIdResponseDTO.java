package server.brainboost.src.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Schema(description = "us DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserIdResponseDTO {

    Long userId;
}
