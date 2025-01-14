package server.brainboost.src.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import server.brainboost.src.medical.entity.MedicalChecklistEntity;
import server.brainboost.src.user.entity.UserEntity;

public interface MedicalChecklistRepository extends JpaRepository<MedicalChecklistEntity, Long> {

	@Modifying
	@Transactional
	@Query("DELETE FROM UserAllergyEntity ua WHERE ua.user = :user")
	int deleteUserAllergy(@Param("user") UserEntity user);

	@Modifying
	@Transactional
	@Query("DELETE FROM UserConditionEntity uc WHERE uc.user = :user")
	int deleteUserCondition(@Param("user") UserEntity user);

	@Modifying
	@Transactional
	@Query("DELETE FROM UserDiscomfortEntity ud WHERE ud.user = :user")
	int deleteUserDiscomfort(@Param("user") UserEntity user);

	@Modifying
	@Transactional
	@Query("DELETE FROM UserMedicineEntity um WHERE um.user = :user")
	int deleteUserMedicine(@Param("user") UserEntity user);

	@Modifying
	@Transactional
	@Query("DELETE FROM UserPregnancyEntity usp WHERE usp.user = :user")
	int deleteUserPregnancy(@Param("user") UserEntity user);




}
