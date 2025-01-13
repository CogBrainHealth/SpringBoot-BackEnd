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
import server.brainboost.enums.AllergyTag;
import server.brainboost.src.user.entity.UserEntity;

@Entity
@Getter
@Table(name = "user_allergy")
@NoArgsConstructor
@DynamicInsert
public class UserAllergyEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "allergy_tag")
	private AllergyTag allergyTag;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	public UserAllergyEntity(AllergyTag allergyTag, UserEntity user){
		this.allergyTag = allergyTag;
		this.user = user;
	}

}
