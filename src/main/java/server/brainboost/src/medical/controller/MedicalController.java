package server.brainboost.src.medical.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponse;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.config.GameTypeName;
import server.brainboost.src.medical.dto.MedicalChecklistDTO;
import server.brainboost.src.medical.dto.NutrientSuggestionDto;
import server.brainboost.src.medical.service.MedicalService;
import server.brainboost.utils.SecurityUtil;

@RestController
@RequiredArgsConstructor
public class MedicalController {

    private final MedicalService medicalService;

    @PostMapping("/api/medical/checklist")
    @Operation(summary = "기본 건강 체크 리스트 작성/수정 api", description = "건강 ", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
    })
    public BaseResponse<String> createMedicalCheckList(@Valid @RequestBody MedicalChecklistDTO medicalCheckListDTO){

       try{
           Long userId = SecurityUtil.getCurrentUserId()
                   .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

           medicalService.createMedicalCheckList(userId, medicalCheckListDTO);
           return new BaseResponse<>("기본 건강 체크 리스트가 작성됐습니다");

       }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
       }

    }

    @GetMapping("/api/medical/nutrient/recommend/attention")
    @Operation(summary = "주의력 부문 영양성분 추천 api", description = "건강 ", responses = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "파라미터 오류"),
    })
    public BaseResponse<NutrientSuggestionDto> recommendAttentionNutrients(){

        try{
            Long userId = SecurityUtil.getCurrentUserId()
                .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            NutrientSuggestionDto nutrientSuggestionDto = medicalService.recommendNutrients(userId, GameTypeName.ATTENTION);
            return new BaseResponse<>(nutrientSuggestionDto);

        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/api/medical/nutrient/recommend/spatial-perception")
    @Operation(summary = "공간지각능력 부문 영양성분 추천 api", description = "건강 ", responses = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "파라미터 오류"),
    })
    public BaseResponse<NutrientSuggestionDto> recommendSpatialPerceptionNutrients(){

        try{
            Long userId = SecurityUtil.getCurrentUserId()
                .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            NutrientSuggestionDto nutrientSuggestionDto = medicalService.recommendNutrients(userId, GameTypeName.SPATIAL_PERCEPTION);
            return new BaseResponse<>(nutrientSuggestionDto);

        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/api/medical/nutrient/recommend/memory")
    @Operation(summary = "기억력 부문 영양성분 추천 api", description = "건강 ", responses = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "파라미터 오류"),
    })
    public BaseResponse<NutrientSuggestionDto> recommendMemoryNutrients(){

        try{
            Long userId = SecurityUtil.getCurrentUserId()
                .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            NutrientSuggestionDto nutrientSuggestionDto = medicalService.recommendNutrients(userId, GameTypeName.MEMORY);
            return new BaseResponse<>(nutrientSuggestionDto);

        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


}
