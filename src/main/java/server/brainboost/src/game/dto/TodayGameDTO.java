package server.brainboost.src.game.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.brainboost.config.CognitiveDomain;

@Getter
@Setter
@Schema(description = "오늘의 게임 조회 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodayGameDTO {

	//game
	private Long todayGameId;
	private String todayGameName;
	private String todayGameImgUrl;
	private String todayGameDescription;
	private String todayGameVersion;
	private CognitiveDomain cognitiveDomain;

}
