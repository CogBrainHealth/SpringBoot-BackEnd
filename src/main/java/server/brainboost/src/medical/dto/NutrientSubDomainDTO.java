package server.brainboost.src.medical.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "NutrientRecommendDTO 중 보조 추천 성분을 반환하는 DTO ")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NutrientSubDomainDTO {

	List<NutrientDTO> nutrientDTOList;
}
