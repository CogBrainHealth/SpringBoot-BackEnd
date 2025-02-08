package server.brainboost.src.medical.dto;

import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "프리미엄 개인 정보 건강 리스트 정보를 담을 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PremiumMedicalChecklistDTO {

	@Schema(description = "1번 문항", nullable = false, example = "1")
	private Integer number1;

	@Schema(description = "2번 문항", nullable = false, example = "3")
	private Integer number2;

	@Schema(description = "3번 문항", nullable = false, example = "1")
	private Integer number3;

	@Schema(description = "4번 문항", nullable = false, example = "1")
	private Integer number4;

	@Schema(description = "5번 문항", nullable = false, example = "1")
	private Integer number5;

	@Schema(description = "6번 문항", nullable = false, example = "1")
	private Integer number6;

	@Schema(description = "7번 문항", nullable = false, example = "1")
	private Integer number7;

	@Schema(description = "8번 문항", nullable = false, example = "1")
	private Integer number8;

	@Schema(description = "9번 문항", nullable = false, example = "1")
	private Integer number9;

	@Schema(description = "10번 문항", nullable = false, example = "1")
	private Integer number10;

	@Schema(description = "11번 문항", nullable = false, example = "1")
	private Integer number11;

	@Schema(description = "12번 문항", nullable = false, example = "1")
	private Integer number12;

	@Schema(description = "13번 문항", nullable = false, example = "1")
	private Integer number13;

	@Schema(description = "걸린 시간", nullable = true, example = "14:30:15")
	private LocalTime localTime;

	public Integer getTotalScore(){
		return number1 + number2 + number3+ number4 + number5 + number6 + number7 + number8 + number9
			+ number10 + number11 + number12 + number13;
	}

}
