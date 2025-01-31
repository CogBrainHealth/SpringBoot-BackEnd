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
@NoArgsConstructor
@AllArgsConstructor
public class NutrientDetails {

	private Boolean isFdaApprovedIngredient;
	private List<String> keyFunctionsSummary;
	private List<String> ingredientDescription;
	private List<String> intakeInstructions;
	private List<String> warning;
	private List<String> specialConditions;
	private List<String> allergyInfo;
	private List<String> contraindicatedDrugs;
	private List<String> precautionsForChronicConditions;
	private List<String> pharmacistComments;


}
