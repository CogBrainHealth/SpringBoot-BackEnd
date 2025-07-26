package server.brainboost.src.medical.entity.check;

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
import server.brainboost.enums.Possibility;
import server.brainboost.enums.PregnancyTag;
import server.brainboost.src.medical.entity.nutrient.NutrientEntity;

@Entity
@Getter
@Table(name = "check_pregnancy_safety")
@NoArgsConstructor
@DynamicInsert
public class CheckPregnancySafetyEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "pregnancy_tag")
	private PregnancyTag pregnancyTag;

	@Enumerated(EnumType.STRING)
	@Column(nullable = true, name = "possibility")
	private Possibility possibility;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nutrient_id")
	private NutrientEntity nutrient;

}
