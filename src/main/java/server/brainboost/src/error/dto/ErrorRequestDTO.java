package server.brainboost.src.error.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

public class ErrorRequestDTO {


    @Getter
    @Schema(description = "프론트의 에러 메세지를 받아오는 DTO")
    public static class FrontendErrorDTO {

        @Schema(description = "페이지 이름", nullable = false)
        @NotBlank(message = "에러 이름을 입력하세요")
        @Size(min = 1, max = 20)
        private String errorName;

        @Schema(description = "에러 메시지", nullable = false)
        @NotBlank(message = "에러 메시지를 입력하세요")
        @Size(min = 1, max = 50)
        private String errorMessage;


    }
}
