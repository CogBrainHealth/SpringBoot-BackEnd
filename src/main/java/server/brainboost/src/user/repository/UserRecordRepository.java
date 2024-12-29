package server.brainboost.src.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.brainboost.src.user.entity.UserRecordEntity;

public interface UserRecordRepository extends JpaRepository<UserRecordEntity, Long> {
}
