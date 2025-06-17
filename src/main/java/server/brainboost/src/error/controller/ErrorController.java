package server.brainboost.src.error.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponse;
import server.brainboost.src.error.dto.ErrorRequestDTO;
import server.brainboost.src.error.service.ErrorService;

@RestController
@RequiredArgsConstructor
public class ErrorController {

	private final ErrorService errorService;

	@PostMapping("/api/error/log")
	@Operation(summary = "서버로 프론트엔드 에러 로그를 보내는 api", description = "에러 제목, 에러 메세지가 필수로 들어가야합니다 ", responses = {
		@ApiResponse(responseCode = "200", description = "성공"),
		@ApiResponse(responseCode = "400", description = "파라미터 오류"),
	})
	public ResponseEntity<BaseResponse<String>> sendErrorMessageToServer(@Valid @RequestBody ErrorRequestDTO.FrontendErrorDTO frontendErrorDTO){

		try{
			errorService.sendErrorMessageToServer(frontendErrorDTO);
			return ResponseEntity.ok(new BaseResponse<>("서버로 에러 메세지가 전달됐습니다"));
		}catch (BaseException e){
			HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
		}



	}


}
