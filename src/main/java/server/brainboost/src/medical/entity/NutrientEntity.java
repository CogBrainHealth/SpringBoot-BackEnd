package server.brainboost.src.medical.entity;

import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.brainboost.base.BaseEntity;
import server.brainboost.enums.NutrientName;
import server.brainboost.src.medical.dto.test.NutrientDetails;

@Entity
@Getter
@Table(name = "nutrient")
@NoArgsConstructor
@DynamicInsert
public class NutrientEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "nutrient_name")
	private NutrientName nutrientName;

	@Column(columnDefinition = "text")
	private String details; // 모든 JSON 데이터를 하나의 필드에 저장


	// JSON 변환을 위한 메서드 추가 (ObjectMapper 사용)
	public NutrientDetails getDetailsAsObject() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(this.details, NutrientDetails.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("JSON 변환 오류", e);
		}
	}

	public void setDetailsFromObject(NutrientDetails nutrientDetails) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			this.details = objectMapper.writeValueAsString(nutrientDetails);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("JSON 변환 오류", e);
		}
	}



}
