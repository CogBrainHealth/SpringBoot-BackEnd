package server.brainboost.src.medical.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.brainboost.code.ApiResponse;
import server.brainboost.exception.BaseException;
import server.brainboost.base.BaseResponse;
import server.brainboost.enums.CognitiveDomain;
import server.brainboost.src.medical.dto.*;
import server.brainboost.src.medical.service.MedicalService;
import server.brainboost.utils.SecurityUtil;

@RestController
@RequiredArgsConstructor
public class MedicalController {

    private final MedicalService medicalService;

    @PostMapping("/api/medical/checklist")
    @Operation(summary = "기본 건강 체크 리스트 작성 api", description = "체크리스트 작성", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "성별이 올바르지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "기본 건강 정보를 이미 작성하셨습니다"),
    })
    public ApiResponse<MedicalResponseDTO.MedicalChecklistResponseDTO> createMedicalCheckList(@Valid @RequestBody MedicalRequestDTO.MedicalChecklistRequestDTO medicalCheckListRequestDTO){

        Long userId = SecurityUtil.getCurrentUserId();
        MedicalResponseDTO.MedicalChecklistResponseDTO medicalChecklistResponseDTO = medicalService.createMedicalCheckList(userId, medicalCheckListRequestDTO);
        return ApiResponse.onSuccess(medicalChecklistResponseDTO);


    }

    @PatchMapping("/api/medical/checklist")
    @Operation(summary = "기본 건강 체크 리스트 수정 api", description = "체크리스트 수정 ", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "성별이 올바르지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "기본 건강 정보를 아직 작성하지 않으셨습니다"),
    })
    public ApiResponse<MedicalResponseDTO.MedicalChecklistResponseDTO> updateMedicalCheckList(@Valid @RequestBody MedicalRequestDTO.MedicalChecklistRequestDTO medicalCheckListRequestDTO){

        Long userId = SecurityUtil.getCurrentUserId();
        MedicalResponseDTO.MedicalChecklistResponseDTO medicalChecklistResponseDTO = medicalService.updateMedicalCheckList(userId, medicalCheckListRequestDTO);
        return ApiResponse.onSuccess(medicalChecklistResponseDTO);

    }



    @PostMapping("/api/medical/premium/checklist")
    @Operation(summary = "프리미엄 건강 체크 결과 작성 api", description = "PremiumMedicalChecklistDTO 정보를 받아 프리미엄 건강 체크 리스트 작성 ", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "프리미엄 유저가 아닙니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "프리미엄 건강 정보를 이미 작성하셨습니다"),
    })
    public ApiResponse<String> createPremiumMedicalCheckList(@Valid @RequestBody
                                                                              MedicalRequestDTO.PremiumMedicalChecklistDTO premiumMedicalChecklistDTO){

        Long userId = SecurityUtil.getCurrentUserId();

        medicalService.createPremiumMedicalCheckList(userId, premiumMedicalChecklistDTO);
        return ApiResponse.onSuccess("프리미엄 건강 체크리스트 정보가 작성되었습니다");


    }

    @PatchMapping("/api/medical/premium/checklist")
    @Operation(summary = "프리미엄 건강 체크 리스트 수정 api", description = "PremiumMedicalChecklistDTO 정보를 받아 프리미엄 건강 체크 리스트 수정 ", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "프리미엄 유저가 아닙니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "프리미엄 건강 정보를 아직 작성하지 않으셨습니다"),
    })
    public ResponseEntity<BaseResponse<String>> updatePremiumMedicalCheckList(@Valid @RequestBody
                                                                              MedicalRequestDTO.PremiumMedicalChecklistDTO premiumMedicalChecklistDTO){

        try{
            Long userId = SecurityUtil.getCurrentUserId();

            medicalService.updatePremiumMedicalCheckList(userId, premiumMedicalChecklistDTO);
            return ResponseEntity.ok(new BaseResponse<>("프리미엄 건강 체크 리스트가 작성됐습니다"));

        }catch (BaseException e){
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));

        }


    }


    @GetMapping("/api/medical/checklist")
    @Operation(summary = "기본 건강 체크 리스트 조회 api", description = "체크 리스트 조회", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "성별이 올바르지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "기본 건강 정보를 아직 작성하지 않으셨습니다"),
    })
    public ApiResponse<MedicalResponseDTO.MedicalChecklistResponseDTO> getMedicalCheckList(){
            Long userId = SecurityUtil.getCurrentUserId();

            MedicalResponseDTO.MedicalChecklistResponseDTO medicalChecklistResponseDTO = medicalService.getMedicalCheckList(userId);
            return ApiResponse.onSuccess(medicalChecklistResponseDTO);

    }


    @GetMapping("/api/medical/nutrient/recommend/attention")
    @Operation(summary = "주의력 부문 영양성분 추천 api", description = "단일 영역 영양성분 추천(주요 성분과 보조 성분으로 나눠 추천) ", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "데이터베이스 에러입니다"),
    })
    public ApiResponse<MedicalResponseDTO.NutrientSuggestionDto> recommendAttentionNutrients(){

        Long userId = SecurityUtil.getCurrentUserId();

        MedicalResponseDTO.NutrientSuggestionDto nutrientSuggestionDto = medicalService.recommendNutrients(userId, CognitiveDomain.ATTENTION);
        return ApiResponse.onSuccess(nutrientSuggestionDto);


    }


    @GetMapping("/api/medical/nutrient/recommend/spatial-perception")
    @Operation(summary = "공간지각능력 부문 영양성분 추천 api", description = "단일 영역 영양성분 추천(주요 성분과 보조 성분으로 나눠 추천) ", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "데이터베이스 에러입니다"),
    })
    public ApiResponse<MedicalResponseDTO.NutrientSuggestionDto> recommendSpatialPerceptionNutrients(){


        Long userId = SecurityUtil.getCurrentUserId();

        MedicalResponseDTO.NutrientSuggestionDto nutrientSuggestionDto = medicalService.recommendNutrients(userId, CognitiveDomain.SPATIAL_PERCEPTION);
        return ApiResponse.onSuccess(nutrientSuggestionDto);


    }


    @GetMapping("/api/medical/nutrient/recommend/memory")
    @Operation(summary = "기억력 부문 영양성분 추천 api", description = "단일 영역 영양성분 추천(주요 성분과 보조 성분으로 나눠 추천) ", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "데이터베이스 에러입니다"),
    })
    public ApiResponse<MedicalResponseDTO.NutrientSuggestionDto> recommendMemoryNutrients(){


        Long userId = SecurityUtil.getCurrentUserId();

        MedicalResponseDTO.NutrientSuggestionDto nutrientSuggestionDto = medicalService.recommendNutrients(userId, CognitiveDomain.MEMORY);
        return ApiResponse.onSuccess(nutrientSuggestionDto);


    }


    /*@PostMapping("/api/medical/nutrient/wrtie/details")
    @Operation(summary = "nutrient 영양소 정보를 저장하는 api", description = "테스트 api) ", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "데이터베이스 에러입니다"),
    })
    public ResponseEntity<BaseResponse<String>> getNutrientInfo(){

        try{

            medicalService.changeNutrientDetails();
            return ResponseEntity.ok(new BaseResponse<>("정상적으로 영양성분 정보가 저장되었습니다"));

        }catch (BaseException e){
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
        }

    }*/

    @GetMapping("/api/medical/nutrient/{nutrient_id}")
    @Operation(summary = "nutrient 영양소 정보를 조회하는 api", description = "nutriendId로 해당 영양소의 정보를 조회" , responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "데이터베이스 에러입니다"),
    })
    public ApiResponse<MedicalResponseDTO.NutrientResponseDTO> getNutrientDetails(@Valid @PathVariable("nutrient_id") Long nutrientId){


        Long userId = SecurityUtil.getCurrentUserId();

        MedicalResponseDTO.NutrientResponseDTO nutrientResponseDTO = medicalService.getNutrientDetails(nutrientId);
        return ApiResponse.onSuccess(nutrientResponseDTO);



    }

}
