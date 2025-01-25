package server.brainboost.src.medical.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponse;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.enums.CognitiveDomain;
import server.brainboost.src.medical.dto.MedicalChecklistDTO;
import server.brainboost.src.medical.dto.NutrientSuggestionDto;
import server.brainboost.src.medical.service.MedicalService;
import server.brainboost.utils.SecurityUtil;

@RestController
@RequiredArgsConstructor
public class MedicalController {

    private final MedicalService medicalService;

    @PostMapping("/api/medical/checklist")
    @Operation(summary = "기본 건강 체크 리스트 작성 api", description = "체크리스트 작성", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
            @ApiResponse(responseCode = "500", description = "성별이 올바르지 않습니다"),
            @ApiResponse(responseCode = "500", description = "기본 건강 정보를 이미 작성하셨습니다"),
    })
    public ResponseEntity<BaseResponse<String>> createMedicalCheckList(@Valid @RequestBody MedicalChecklistDTO medicalCheckListDTO){

       try{
           Long userId = SecurityUtil.getCurrentUserId()
                   .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

           medicalService.createMedicalCheckList(userId, medicalCheckListDTO);
           return ResponseEntity.ok(new BaseResponse<>("기본 건강 체크 리스트가 작성됐습니다"));

       }catch (BaseException e){
           HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
           return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
       }

    }

    @PatchMapping("/api/medical/checklist")
    @Operation(summary = "기본 건강 체크 리스트 수정 api", description = "체크리스트 수정 ", responses = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "파라미터 오류"),
        @ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
        @ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
        @ApiResponse(responseCode = "500", description = "성별이 올바르지 않습니다"),
        @ApiResponse(responseCode = "500", description = "기본 건강 정보를 아직 작성하지 않으셨습니다"),
    })
    public ResponseEntity<BaseResponse<String>> updateMedicalCheckList(@Valid @RequestBody MedicalChecklistDTO medicalCheckListDTO){

        try{
            Long userId = SecurityUtil.getCurrentUserId()
                .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            medicalService.updateMedicalCheckList(userId, medicalCheckListDTO);
            return ResponseEntity.ok(new BaseResponse<>("기본 건강 체크 리스트가 수정됐습니다"));

        }catch (BaseException e){
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
        }

    }

    @GetMapping("/api/medical/checklist")
    @Operation(summary = "기본 건강 체크 리스트 조회 api", description = "체크 리스트 조회", responses = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "파라미터 오류"),
        @ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
        @ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
        @ApiResponse(responseCode = "500", description = "성별이 올바르지 않습니다"),
        @ApiResponse(responseCode = "500", description = "기본 건강 정보를 아직 작성하지 않으셨습니다"),
    })
    public ResponseEntity<BaseResponse<MedicalChecklistDTO>> getMedicalCheckList(){

        try{
            Long userId = SecurityUtil.getCurrentUserId()
                .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            MedicalChecklistDTO medicalChecklistDTO = medicalService.getMedicalCheckList(userId);
            return ResponseEntity.ok(new BaseResponse<>(medicalChecklistDTO));

        }catch (BaseException e){
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));

        }

    }




    @GetMapping("/api/medical/nutrient/recommend/attention")
    @Operation(summary = "주의력 부문 영양성분 추천 api", description = "단일 영역 영양성분 추천(주요 성분과 보조 성분으로 나눠 추천) ", responses = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "파라미터 오류"),
        @ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
        @ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
        @ApiResponse(responseCode = "500", description = "데이터베이스 에러입니다"),
    })
    public ResponseEntity<BaseResponse<NutrientSuggestionDto>> recommendAttentionNutrients(){

        try{
            Long userId = SecurityUtil.getCurrentUserId()
                .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            NutrientSuggestionDto nutrientSuggestionDto = medicalService.recommendNutrients(userId, CognitiveDomain.ATTENTION);
            return ResponseEntity.ok(new BaseResponse<>(nutrientSuggestionDto));

        }catch (BaseException e){
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
        }
    }

    @GetMapping("/api/medical/nutrient/recommend/spatial-perception")
    @Operation(summary = "공간지각능력 부문 영양성분 추천 api", description = "단일 영역 영양성분 추천(주요 성분과 보조 성분으로 나눠 추천) ", responses = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "파라미터 오류"),
        @ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
        @ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
        @ApiResponse(responseCode = "500", description = "데이터베이스 에러입니다"),
    })
    public ResponseEntity<BaseResponse<NutrientSuggestionDto>> recommendSpatialPerceptionNutrients(){

        try{
            Long userId = SecurityUtil.getCurrentUserId()
                .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            NutrientSuggestionDto nutrientSuggestionDto = medicalService.recommendNutrients(userId, CognitiveDomain.SPATIAL_PERCEPTION);
            return ResponseEntity.ok(new BaseResponse<>(nutrientSuggestionDto));

        }catch (BaseException e){
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
        }
    }

    @GetMapping("/api/medical/nutrient/recommend/memory")
    @Operation(summary = "기억력 부문 영양성분 추천 api", description = "단일 영역 영양성분 추천(주요 성분과 보조 성분으로 나눠 추천) ", responses = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "파라미터 오류"),
        @ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
        @ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
        @ApiResponse(responseCode = "500", description = "데이터베이스 에러입니다"),
    })
    public ResponseEntity<BaseResponse<NutrientSuggestionDto>> recommendMemoryNutrients(){

        try{
            Long userId = SecurityUtil.getCurrentUserId()
                .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            NutrientSuggestionDto nutrientSuggestionDto = medicalService.recommendNutrients(userId, CognitiveDomain.MEMORY);
            return ResponseEntity.ok(new BaseResponse<>(nutrientSuggestionDto));

        }catch (BaseException e){
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
        }
    }


}
