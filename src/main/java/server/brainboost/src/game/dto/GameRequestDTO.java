package server.brainboost.src.game.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import server.brainboost.src.game.dto.base.BaseGameResultDTO;

public class GameRequestDTO {


    @Getter
    @Schema(description = "지도보고 길찾기 게임 후, 프론트 단에서 서버쪽에 넘길 데이터(일단 점수만 받지만 최대한 많은 로우 데이터 받기)")
    public static class MapNavigationResultDTO extends BaseGameResultDTO {

        //TODO: row data도 받을 수 있으면 받기

    }

    @Getter
    @Schema(description = "지도보고 길찾기 게임 후, 프론트 단에서 서버쪽에 넘길 데이터 (일단 점수만 받지만 최대한 많은 로우 데이터 받기)")
    public static class MentalRotationDTO extends BaseGameResultDTO {

        //TODO: row data도 받을 수 있으면 받기

    }

    @Getter
    @Schema(description = "scroop_test 후, 프론트 단에서 서버쪽에 넘길 데이터 (일단 점수만 받지만 최대한 많은 로우 데이터 받기)")
    public static class ScroopTestResultDTO extends BaseGameResultDTO {

        //TODO: row data도 받을 수 있으면 받기


    }
}
