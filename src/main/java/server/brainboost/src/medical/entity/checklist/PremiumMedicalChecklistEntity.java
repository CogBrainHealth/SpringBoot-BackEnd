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

	private Integer memoryScore;
	private Integer attentionScore;
	private Integer SpatialPerceptionScore;

	private LocalTime localTime;
	private Integer totalScore;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id") // 외래 키 설정
	private UserEntity user;

	public PremiumMedicalChecklistEntity(MedicalRequestDTO.PremiumMedicalChecklistRequestDTO premiumMedicalChecklistRequestDTO, UserEntity user){
		this.attentionScore = premiumMedicalChecklistRequestDTO.getAttentionScore();
		this.memoryScore = premiumMedicalChecklistRequestDTO.getMemoryScore();
		this.SpatialPerceptionScore = premiumMedicalChecklistRequestDTO.getSpatialPerceptionScore();

		this.totalScore = premiumMedicalChecklistRequestDTO.getTotalScore();

		this.localTime = premiumMedicalChecklistRequestDTO.getLocalTime();
		this.user = user;

	}

	public void updatePremiumChecklistEntity(MedicalRequestDTO.PremiumMedicalChecklistRequestDTO premiumMedicalChecklistRequestDTO){
		this.attentionScore = premiumMedicalChecklistRequestDTO.getAttentionScore();
		this.memoryScore = premiumMedicalChecklistRequestDTO.getMemoryScore();
		this.SpatialPerceptionScore = premiumMedicalChecklistRequestDTO.getSpatialPerceptionScore();

		this.totalScore = premiumMedicalChecklistRequestDTO.getTotalScore();

		this.localTime = premiumMedicalChecklistRequestDTO.getLocalTime();

	}


}
