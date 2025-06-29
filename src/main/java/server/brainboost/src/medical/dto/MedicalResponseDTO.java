package server.brainboost.src.medical.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

public class MedicalResponseDTO {
    @Getter
    @Setter
    @Schema(description = "NutrientRecommendDTO 중 주요 추천 성분을 반환하는 DTO ")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NutrientMainDomainDTO {

        List<NutrientDTO> nutrientDTOList;

    }

    @Getter
    @Setter
    @Schema(description = "NutrientRecommendDTO 중 보조 추천 성분을 반환하는 DTO ")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NutrientSubDomainDTO {

        List<NutrientDTO> nutrientDTOList;
    }

    @Getter
    @Setter
    @Schema(description = "영양성분을 저장하는 DTO ")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NutrientDTO {

        private Long nutrientId;
        private String nutrientName;
        private String details;

    }

    @Getter
    @Setter
    @Schema(description = "사용자에게 추천할 영양성분을 반환하는 DTO ")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NutrientSuggestionDto {

        @Schema(description = "주요 추천 영양성분")
        private NutrientMainDomainDTO nutrientMainDomainDTO;

        @Schema(description = "보조 추천 영양성분")
        private NutrientSubDomainDTO nutrientSubDomainDTO;

    }

    @Getter
    @Setter
    @Schema(description = "영양소 정보를 반환하는 DTO ")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NutrientInfoDTO{

        private Long nutrientId;
        private String nutrientName;
        private String details;

    }

    @Getter
    @Setter
    @Schema(description = "영양소 정보 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NutrientResponseDTO{

        private Long nutrientId;
        private String nutrientName;
        private String details;

    }

}
