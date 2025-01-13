package server.brainboost.src.medical.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "영양성분을 저장하는 DTO ")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NutrientDTO {

	private Long nutrientId;
	private String nutrientName;
	private String description;

}
