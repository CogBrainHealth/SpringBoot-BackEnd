package server.brainboost.src.medical.entity.checklist;

import java.time.LocalTime;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.brainboost.base.BaseEntity;
import server.brainboost.src.medical.dto.MedicalRequestDTO;
import server.brainboost.src.user.entity.UserEntity;

@Entity
@Getter
@Table(name = "premium_medical_checklist")
@NoArgsConstructor
@DynamicInsert
public class PremiumMedicalChecklistEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer number1;
	private Integer number2;
	private Integer number3;
	private Integer number4;
	private Integer number5;
	private Integer number6;
	private Integer number7;
	private Integer number8;
	private Integer number9;
	private Integer number10;
	private Integer number11;
	private Integer number12;
	private Integer number13;

	private LocalTime localTime;
	private Integer totalScore;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id") // 외래 키 설정
	private UserEntity user;

	public PremiumMedicalChecklistEntity(MedicalRequestDTO.PremiumMedicalChecklistDTO premiumMedicalChecklistDTO, UserEntity user){
		this.number1 = premiumMedicalChecklistDTO.getNumber1();
		this.number2 = premiumMedicalChecklistDTO.getNumber2();
		this.number3 = premiumMedicalChecklistDTO.getNumber3();
		this.number4 = premiumMedicalChecklistDTO.getNumber4();
		this.number5 = premiumMedicalChecklistDTO.getNumber5();
		this.number6 = premiumMedicalChecklistDTO.getNumber6();
		this.number7 = premiumMedicalChecklistDTO.getNumber7();
		this.number8 = premiumMedicalChecklistDTO.getNumber8();
		this.number9 = premiumMedicalChecklistDTO.getNumber9();
		this.number10 = premiumMedicalChecklistDTO.getNumber10();
		this.number11 = premiumMedicalChecklistDTO.getNumber11();
		this.number12 = premiumMedicalChecklistDTO.getNumber12();
		this.number13 = premiumMedicalChecklistDTO.getNumber13();

		this.localTime = premiumMedicalChecklistDTO.getLocalTime();
		this.user = user;

		this.totalScore = premiumMedicalChecklistDTO.getTotalScore();
	}

	public void updatePremiumChecklistEntity(MedicalRequestDTO.PremiumMedicalChecklistDTO premiumMedicalChecklistDTO){
		this.number1 = premiumMedicalChecklistDTO.getNumber1();
		this.number2 = premiumMedicalChecklistDTO.getNumber2();
		this.number3 = premiumMedicalChecklistDTO.getNumber3();
		this.number4 = premiumMedicalChecklistDTO.getNumber4();
		this.number5 = premiumMedicalChecklistDTO.getNumber5();
		this.number6 = premiumMedicalChecklistDTO.getNumber6();
		this.number7 = premiumMedicalChecklistDTO.getNumber7();
		this.number8 = premiumMedicalChecklistDTO.getNumber8();
		this.number9 = premiumMedicalChecklistDTO.getNumber9();
		this.number10 = premiumMedicalChecklistDTO.getNumber10();
		this.number11 = premiumMedicalChecklistDTO.getNumber11();
		this.number12 = premiumMedicalChecklistDTO.getNumber12();
		this.number13 = premiumMedicalChecklistDTO.getNumber13();

		this.localTime = premiumMedicalChecklistDTO.getLocalTime();

		this.totalScore = premiumMedicalChecklistDTO.getTotalScore();
	}


}
