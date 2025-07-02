package server.brainboost.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import server.brainboost.code.ApiResponse;
import server.brainboost.code.status.ErrorStatus;
import server.brainboost.exception.BaseException;
import server.brainboost.base.BaseResponse;
import server.brainboost.base.BaseResponseStatus;

public class ResponseUtil {

	//TODO ApiResponse로 바꾼 결과 test 해보기
	public static void handleResponse(HttpServletResponse response, Object object) throws IOException{
		response.setStatus(HttpServletResponse.SC_OK); // 또는 e.getStatusCode()
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		// BaseResponse 형태의 에러 응답 작성
		ApiResponse<Object> apiResponse = ApiResponse.onSuccess(object);
		// BaseResponse<Object> baseResponse = new BaseResponse<>(object);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonResponse = objectMapper.writeValueAsString(apiResponse);

		// 응답 출력
		response.getWriter().write(jsonResponse);
		response.getWriter().flush();
	}

	public static void handleException(HttpServletResponse response, ErrorStatus errorStatus) {

		try{
			// HTTP 상태 코드 설정
			response.setStatus(errorStatus.getHttpStatus().value()); // 또는 e.getStatusCode()
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			// BaseResponse 형태의 에러 응답 작성
			ApiResponse<Object> apiResponse = ApiResponse.onFailure(errorStatus, null);
			// BaseResponse<BaseResponseStatus> errorResponse = new BaseResponse<>(e.getStatus());
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonResponse = objectMapper.writeValueAsString(apiResponse);

			// 응답 출력
			response.getWriter().write(jsonResponse);
			response.getWriter().flush();
		}catch (IOException ioException){
			throw new RuntimeException(ioException);
		}

	}


	/*public static void handleException(HttpServletResponse response, int status ,BaseException e) {

		try{
			// HTTP 상태 코드 설정
			response.setStatus(status); // 또는 e.getStatusCode()
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			// BaseResponse 형태의 에러 응답 작성
			BaseResponse<BaseResponseStatus> errorResponse = new BaseResponse<>(e.getStatus());
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonResponse = objectMapper.writeValueAsString(errorResponse);

			// 응답 출력
			response.getWriter().write(jsonResponse);
			response.getWriter().flush();
		}catch (IOException ioException){
			throw new RuntimeException(ioException);
		}

	}*/



}
