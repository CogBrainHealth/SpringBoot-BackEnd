package server.brainboost.src.game.dto.base;

import io.swagger.v3.oas.annotations.media.Schema;
import server.brainboost.src.statistics.entity.enums.AgeGroup;

public abstract class BaseGameResultDTO {

    @Schema(description = "나잇대 정보, ex. TWENTIES")
    protected AgeGroup ageGroup;


    public AgeGroup getAgeGroup() { return ageGroup; }

    // public abstract void processRawData();
}
