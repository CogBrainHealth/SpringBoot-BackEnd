package server.brainboost.src.medical.entity.domain;

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
import server.brainboost.src.medical.entity.nutrient.enums.CognitiveDomain;
import server.brainboost.src.medical.entity.domain.enums.DomainType;
import server.brainboost.src.medical.entity.nutrient.NutrientEntity;

@Entity
@Getter
@Table(name = "nutrient_domain")
@NoArgsConstructor
@DynamicInsert
public class NutrientDomainEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "cognitive_domain")
	private CognitiveDomain cognitiveDomain;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "domain_type")
	private DomainType domainType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nutrient_id")
	private NutrientEntity nutrient;


}
