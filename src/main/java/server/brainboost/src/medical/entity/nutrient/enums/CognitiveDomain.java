package server.brainboost.src.medical.entity.nutrient.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import server.brainboost.code.status.ErrorStatus;
import server.brainboost.exception.GeneralException;

public enum CognitiveDomain {

    ATTENTION, SPATIAL_PERCEPTION, MEMORY;

    @JsonCreator
    public static CognitiveDomain from(String value) {

        if (value == null || value.isBlank()) {
            throw new GeneralException(ErrorStatus.PARAMETER_ERROR);
        }

        return CognitiveDomain.valueOf(value.toUpperCase());
    }

}
