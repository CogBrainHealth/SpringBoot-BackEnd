package server.brainboost.src.medical.entity.userStatus;

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
import server.brainboost.src.medical.entity.check.enums.DiscomfortTag;
import server.brainboost.src.user.entity.UserEntity;

@Entity
@Getter
@Table(name = "user_discomfort")
@NoArgsConstructor
@DynamicInsert
public class UserDiscomfortEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "discomfort_tag")
	private DiscomfortTag discomfortTag;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	public UserDiscomfortEntity(DiscomfortTag discomfortTag, UserEntity user){
		this.discomfortTag = discomfortTag;
		this.user = user;
	}

}
