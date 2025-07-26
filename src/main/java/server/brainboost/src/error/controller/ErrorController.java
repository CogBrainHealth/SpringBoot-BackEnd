package server.brainboost.src.error.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import server.brainboost.code.ApiResponse;
import server.brainboost.src.error.dto.ErrorRequestDTO;
import server.brainboost.src.error.service.ErrorService;

@RestController
@RequiredArgsConstructor
public class ErrorController {

	private final ErrorService errorService;

	@PostMapping("/api/error/log")
	@Operation(summary = "서버로 프론트엔드 에러 로그를 보내는 api", description = "에러 제목, 에러 메세지가 필수로 들어가야합니다 ", responses = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
	})
	public ApiResponse<String> sendErrorMessageToServer(@Valid @RequestBody ErrorRequestDTO.FrontendErrorDTO frontendErrorDTO){

		errorService.sendErrorMessageToServer(frontendErrorDTO);
		return ApiResponse.onSuccess("서버로 에러 메시지가 전달 됏습니다");

	}


}
