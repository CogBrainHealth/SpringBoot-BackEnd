package server.brainboost.src.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import server.brainboost.src.user.entity.RefreshEntity;

public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {

	Boolean existsByRefresh(String refresh);

	@Transactional
	int deleteByRefresh(String refresh);

}
