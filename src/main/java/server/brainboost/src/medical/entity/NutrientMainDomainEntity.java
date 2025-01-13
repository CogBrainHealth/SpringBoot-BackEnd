package server.brainboost.src.medical.entity;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.brainboost.base.BaseEntity;
import server.brainboost.config.GameTypeName;
import server.brainboost.enums.MedicineTag;

@Entity
@Getter
@Table(name = "nutrient_main_domain")
@NoArgsConstructor
@DynamicInsert
public class NutrientMainDomainEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "cognitive_domain")
	private GameTypeName gameTypeName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nutrient_id")
	private NutrientEntity nutrient;


}
