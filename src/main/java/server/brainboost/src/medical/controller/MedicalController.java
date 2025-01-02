package server.brainboost.src.medical.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponse;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.src.medical.dto.MedicalChecklistDTO;
import server.brainboost.src.medical.service.MedicalService;
import server.brainboost.utils.SecurityUtil;

@RestController
@RequiredArgsConstructor
public class MedicalController {

    private final MedicalService medicalService;

    @PatchMapping("/api/medicals/checklist")
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



}
