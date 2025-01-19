package server.brainboost.src.error.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import server.brainboost.base.BaseException;
import server.brainboost.src.error.dto.FrontendErrorDTO;
import server.brainboost.src.error.entity.FrontendErrorLogEntity;
import server.brainboost.src.error.repository.FrontendErrorLogRepository;

@Service
@RequiredArgsConstructor
public class ErrorService {

	private final FrontendErrorLogRepository frontendErrorLogRepository;
	public void sendErrorMessageToServer(FrontendErrorDTO frontendErrorDTO) throws BaseException {

		FrontendErrorLogEntity frontendErrorLog = new FrontendErrorLogEntity(frontendErrorDTO);
		frontendErrorLogRepository.save(frontendErrorLog);

	}
}
