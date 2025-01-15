package server.brainboost.config;

import com.fasterxml.jackson.annotation.JsonCreator;

import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponseStatus;

public enum CognitiveDomain {

    ATTENTION, SPATIAL_PERCEPTION, MEMORY;

    @JsonCreator
    public static CognitiveDomain from(String value) {

        if (value == null || value.isBlank()) {
            throw new BaseException(BaseResponseStatus.PARAMETER_ERROR);
        }

        return CognitiveDomain.valueOf(value.toUpperCase());
    }

}
