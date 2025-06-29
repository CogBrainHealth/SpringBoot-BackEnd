package server.brainboost.src.medical.dto.converter;

import server.brainboost.src.medical.dto.MedicalResponseDTO;
import server.brainboost.src.medical.entity.nutrient.NutrientEntity;

public class MedicalConverter {

    public static MedicalResponseDTO.NutrientResponseDTO toNutrientInfoDTO(NutrientEntity nutrientEntity) {
        return MedicalResponseDTO.NutrientResponseDTO.builder()
                .nutrientId(nutrientEntity.getId())
                .nutrientName(nutrientEntity.getNutrientName().name())
                .details(nutrientEntity.getDetails())
                .build();


    }

}
