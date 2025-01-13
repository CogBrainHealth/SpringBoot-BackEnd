package server.brainboost.src.medical.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "사용자에게 추천할 영양성분을 반환하는 DTO ")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NutrientSuggestionDto {

	private NutrientMainDomainDTO nutrientMainDomainDTO;
	private NutrientSubDomainDTO nutrientSubDomainDTO;

}
