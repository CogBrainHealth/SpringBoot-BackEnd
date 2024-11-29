package server.brainboost.src.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Schema(description = "Object Mapper을 통해 json 받아올 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttemptAutenticationDTO {

    private String username;
    private String password;
}
