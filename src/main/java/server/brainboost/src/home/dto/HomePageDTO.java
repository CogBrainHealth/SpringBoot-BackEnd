package server.brainboost.src.home.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Schema(description = "홈페이지 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomePageDTO {

    //user
    private Long userId;
    private String nickname;
    private String profileImgUrl;

    //game
    private Long todayGameId;
    private String todayGameName;
    private String todayGameImgUrl;
    private String todayGameDescription;

}
