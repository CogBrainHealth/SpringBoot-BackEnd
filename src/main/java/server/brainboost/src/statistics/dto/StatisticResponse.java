package server.brainboost.src.statistics.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import server.brainboost.src.statistics.entity.enums.AgeGroup;

public class StatisticResponse {

    @Getter
    @Setter
    @Schema(description = "게임 통계 정보 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GameStatisticsDTO {

        @Schema(description = "전체 점수")
        private int totalScore;

        @Schema(description = "주의력 영역 점수")
        private int attentionScore;

        @Schema(description = "공간지각능력 점수")
        private int SpatialPerceptionScore;

        @Schema(description = "기억력 영역 점수")
        private int MemoryScore;


    }

    @Getter
    @Setter
    @Schema(description = "총합 점수 통계 DTO")
    @Builder
    public static class TotalScoreResponseDTO {


        @Schema(description = "전체 점수")
        public final int totalScore;

        @Schema(description = "전체 점수에 대한 설명")
        public final String description;

        public TotalScoreResponseDTO(int score, String description) {
            this.totalScore = score;
            this.description = description;
        }


    }

    @Getter
    @Setter
    @Schema(description = "통계 기본 화면 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StatisticsHomeResponseDTO {


        @Schema(description = "전체 점수")
        private int totalScore = 0;

        @Schema(description = "주의력 영역 점수")
        private int attentionScore = 0;

        @Schema(description = "공간지각능력 점수")
        private int SpatialPerceptionScore = 0;

        @Schema(description = "기억력 영역 점수")
        private int MemoryScore = 0;


    }

    @Getter
    @Setter
    @Schema(description = "주의력 점수 통계 DTO")
    @Builder
    public static class AttentionScoreResponseDTO {

        @Schema(description = "나잇대(결과)")
        public final AgeGroup ageGroup;

        @Schema(description = "전체 점수에 대한 설명")
        public final String description;

        public AttentionScoreResponseDTO(AgeGroup ageGroup, String description) {
            this.ageGroup = ageGroup;
            this.description = description;
        }

    }

    @Getter
    @Setter
    @Schema(description = "기억력 점수 통계 DTO")
    @Builder
    public static class MemoryScoreResponseDTO {

        @Schema(description = "나잇대(결과)")
        public final AgeGroup ageGroup;

        @Schema(description = "전체 점수에 대한 설명")
        public final String description;

        public MemoryScoreResponseDTO(AgeGroup ageGroup, String description) {
            this.ageGroup = ageGroup;
            this.description = description;
        }

    }

    @Getter
    @Setter
    @Schema(description = "공간 지각력 점수 통계 DTO")
    @Builder
    public static class SpatialPerceptionScoreResponseDTO {

        @Schema(description = "나잇대(결과)")
        public final AgeGroup ageGroup;

        @Schema(description = "전체 점수에 대한 설명")
        public final String description;

        public SpatialPerceptionScoreResponseDTO(AgeGroup ageGroup, String description) {
            this.ageGroup = ageGroup;
            this.description = description;
        }

    }

}
