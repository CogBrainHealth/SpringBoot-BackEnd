package server.brainboost.src.error.entity;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.brainboost.base.BaseEntity;
import server.brainboost.src.error.dto.ErrorRequestDTO;

@Entity
@Getter
@Table(name = "frontend_error_log")
@NoArgsConstructor
@DynamicInsert
public class FrontendErrorLogEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long frontendErrorLogId;

	@Column(name = "error_name", nullable = false)
	private String errorName;

	@Column(name = "error_message", nullable = false)
	private String errorMessage;


	public FrontendErrorLogEntity(ErrorRequestDTO.FrontendErrorDTO frontendErrorDTO){
		this.errorName = frontendErrorDTO.getErrorName();
		this.errorMessage = frontendErrorDTO.getErrorMessage();
	}

}
