package server.brainboost.src.error.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import server.brainboost.src.error.entity.FrontendErrorLogEntity;

public interface FrontendErrorLogRepository extends JpaRepository<FrontendErrorLogEntity, Long> {


}
