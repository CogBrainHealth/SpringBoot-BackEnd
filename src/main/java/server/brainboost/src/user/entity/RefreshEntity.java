package server.brainboost.src.user.entity;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.brainboost.base.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "refresh")
@NoArgsConstructor
@DynamicInsert
public class RefreshEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String refresh;
	private String expiration;



}
