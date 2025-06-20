package server.brainboost.src.error.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import server.brainboost.exception.BaseException;
import server.brainboost.exception.GeneralException;
import server.brainboost.src.error.dto.ErrorRequestDTO;
import server.brainboost.src.error.entity.FrontendErrorLogEntity;
import server.brainboost.src.error.repository.FrontendErrorLogRepository;

@Service
@RequiredArgsConstructor
public class ErrorService {

	private final FrontendErrorLogRepository frontendErrorLogRepository;
	public void sendErrorMessageToServer(ErrorRequestDTO.FrontendErrorDTO frontendErrorDTO) {

		FrontendErrorLogEntity frontendErrorLog = new FrontendErrorLogEntity(frontendErrorDTO);
		frontendErrorLogRepository.save(frontendErrorLog);

	}
}
