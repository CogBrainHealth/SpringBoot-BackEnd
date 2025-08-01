package server.brainboost.src.medical.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import server.brainboost.src.medical.entity.nutrient.enums.CognitiveDomain;
import server.brainboost.src.medical.entity.check.enums.Possibility;
import server.brainboost.src.medical.dto.MedicalResponseDTO;

@Repository
public class MedicalRepository {

	private final JdbcTemplate jdbcTemplate;

	public MedicalRepository(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<MedicalResponseDTO.NutrientDTO> recommendMainNutrientsForMan(Long userId, CognitiveDomain typeName) throws Exception{

		String sql = """
    	SELECT n.id AS nutrient_id, n.nutrient_name AS nutrient_name, n.details AS nutrient_details 
    	FROM nutrient AS n 
    	INNER JOIN nutrient_domain AS nmd ON n.id = nmd.nutrient_id AND nmd.domain_type = 'MAIN'
    	WHERE nmd.cognitive_domain = ?
      	AND EXISTS (
          	SELECT 1 
          	FROM check_recommendation AS cr 
          	INNER JOIN user_discomfort AS ud 
          	ON cr.discomfort_tag = ud.discomfort_tag 
          	WHERE ud.user_id = ? 
            	AND n.id = cr.nutrient_id
      	)
      	AND NOT EXISTS (
          	SELECT 1 
          	FROM check_condition AS nc 
          	INNER JOIN user_condition AS uc 
          	ON nc.condition_tag = uc.condition_tag 
          	WHERE uc.user_id = ?  
            	AND n.id = nc.nutrient_id
      	)
      	AND NOT EXISTS (
         	 SELECT 1 
          	FROM check_allergy_warning AS naw 
          	INNER JOIN user_allergy AS ua 
          	ON naw.allergy_tag = ua.allergy_tag 
          	WHERE ua.user_id = ? 
            	AND n.id = naw.nutrient_id
      	)
      	AND NOT EXISTS (
         	 SELECT 1 
          	FROM check_drug_interaction AS ndi 
          	INNER JOIN user_medicine AS um 
          	ON ndi.medicine_tag = um.medicine_tag 
          	WHERE um.user_id = ? 
            	AND n.id = ndi.nutrient_id
      	);
   			 """;

		List<MedicalResponseDTO.NutrientDTO> nutrientDTOList = jdbcTemplate.query(sql,
			new RowMapper<MedicalResponseDTO.NutrientDTO>() {
				@Override
				public MedicalResponseDTO.NutrientDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					Long nutrientId = rs.getLong("nutrient_id");
					String nutrientName = rs.getString("nutrient_name");
					String description = rs.getString("nutrient_details");

					return new MedicalResponseDTO.NutrientDTO(nutrientId, nutrientName, description);
				}
			},
			typeName.name(), userId, userId, userId, userId
		);

		return nutrientDTOList;
	}


	public List<MedicalResponseDTO.NutrientDTO> recommendSubNutrientsForMan(Long userId, CognitiveDomain typeName) throws Exception{

		String sql = """
    	SELECT n.id AS nutrient_id, n.nutrient_name AS nutrient_name, n.details AS nutrient_details  
    	FROM nutrient AS n 
    	INNER JOIN nutrient_domain AS nmd ON n.id = nmd.nutrient_id AND nmd.domain_type = 'SUB' 
    	WHERE nmd.cognitive_domain = ?
      	AND EXISTS (
          	SELECT 1 
          	FROM check_recommendation AS nr 
          	INNER JOIN user_discomfort AS ud 
          	ON nr.discomfort_tag = ud.discomfort_tag 
          	WHERE ud.user_id = ? 
            	AND n.id = nr.nutrient_id
      	)
      	AND NOT EXISTS (
          	SELECT 1 
          	FROM check_condition AS nc 
          	INNER JOIN user_condition AS uc 
          	ON nc.condition_tag = uc.condition_tag 
          	WHERE uc.user_id = ?  
            	AND n.id = nc.nutrient_id
      	)
      	AND NOT EXISTS (
         	 SELECT 1 
          	FROM check_allergy_warning AS naw 
          	INNER JOIN user_allergy AS ua 
          	ON naw.allergy_tag = ua.allergy_tag 
          	WHERE ua.user_id = ? 
            	AND n.id = naw.nutrient_id
      	)
      	AND NOT EXISTS (
         	 SELECT 1 
          	FROM check_drug_interaction AS ndi 
          	INNER JOIN user_medicine AS um 
          	ON ndi.medicine_tag = um.medicine_tag 
          	WHERE um.user_id = ? 
            	AND n.id = ndi.nutrient_id
      	);
   			 """;

		List<MedicalResponseDTO.NutrientDTO> nutrientDTOList = jdbcTemplate.query(sql,
			new RowMapper<MedicalResponseDTO.NutrientDTO>() {
				@Override
				public MedicalResponseDTO.NutrientDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					Long nutrientId = rs.getLong("nutrient_id");
					String nutrientName = rs.getString("nutrient_name");
					String description = rs.getString("nutrient_details");

					return new MedicalResponseDTO.NutrientDTO(nutrientId, nutrientName, description);
				}
			},
			typeName.name(), userId, userId, userId, userId
		);

		return nutrientDTOList;
	}

	public List<MedicalResponseDTO.NutrientDTO> recommendMainNutrientsForWoman(Long userId, CognitiveDomain typeName) throws Exception{

		String sql = """
    	SELECT n.id AS nutrient_id, n.nutrient_name AS nutrient_name, n.details AS nutrient_details 
    	FROM nutrient AS n 
    	INNER JOIN nutrient_domain AS nmd ON n.id = nmd.nutrient_id AND nmd.domain_type = 'MAIN'
    	WHERE nmd.cognitive_domain = ?
      	AND EXISTS (
          	SELECT 1 
          	FROM check_recommendation AS nr 
          	INNER JOIN user_discomfort AS ud 
          	ON nr.discomfort_tag = ud.discomfort_tag 
          	WHERE ud.user_id = ? 
            	AND n.id = nr.nutrient_id
      	)
      	AND NOT EXISTS(
      		SELECT 1
      		FROM check_pregnancy_safety AS nps
      		INNER JOIN user_pregnancy AS usp
      		ON nps.pregnancy_tag = usp.pregnancy_tag
      		WHERE usp.user_id = ?
      			AND n.id = nps.nutrient_id
      			AND (nps.possibility = ? OR nps.possibility = ?)
      	)
      	AND NOT EXISTS (
          	SELECT 1 
          	FROM check_condition AS nc 
          	INNER JOIN user_condition AS uc 
          	ON nc.condition_tag = uc.condition_tag 
          	WHERE uc.user_id = ?  
            	AND n.id = nc.nutrient_id
      	)
      	AND NOT EXISTS (
         	 SELECT 1 
          	FROM check_allergy_warning AS naw 
          	INNER JOIN user_allergy AS ua 
          	ON naw.allergy_tag = ua.allergy_tag 
          	WHERE ua.user_id = ? 
            	AND n.id = naw.nutrient_id
      	)
      	AND NOT EXISTS (
         	 SELECT 1 
          	FROM check_drug_interaction AS ndi 
          	INNER JOIN user_medicine AS um 
          	ON ndi.medicine_tag = um.medicine_tag 
          	WHERE um.user_id = ? 
            	AND n.id = ndi.nutrient_id
      	);
   			 """;

		List<MedicalResponseDTO.NutrientDTO> nutrientDTOList = jdbcTemplate.query(sql,
			new RowMapper<MedicalResponseDTO.NutrientDTO>() {
				@Override
				public MedicalResponseDTO.NutrientDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					Long nutrientId = rs.getLong("nutrient_id");
					String nutrientName = rs.getString("nutrient_name");
					String description = rs.getString("nutrient_details");

					return new MedicalResponseDTO.NutrientDTO(nutrientId, nutrientName, description);
				}
			},
			typeName.name(), userId, userId, Possibility.AMBIGUOUS.name(), Possibility.IMPOSSIBLE.name(), userId, userId, userId
		);

		return nutrientDTOList;
	}


	public List<MedicalResponseDTO.NutrientDTO> recommendSubNutrientsForWoman(Long userId, CognitiveDomain typeName) throws Exception{

		String sql = """
    	SELECT n.id AS nutrient_id, n.nutrient_name AS nutrient_name, n.details AS nutrient_details 
    	FROM nutrient AS n 
    	INNER JOIN nutrient_domain AS nmd ON n.id = nmd.nutrient_id AND nmd.domain_type = 'SUB'
    	WHERE nmd.cognitive_domain = ?
      	AND EXISTS (
          	SELECT 1 
          	FROM check_recommendation AS nr 
          	INNER JOIN user_discomfort AS ud 
          	ON nr.discomfort_tag = ud.discomfort_tag 
          	WHERE ud.user_id = ? 
            	AND n.id = nr.nutrient_id
      	)
      	AND NOT EXISTS(
      		SELECT 1
      		FROM check_pregnancy_safety AS nps
      		INNER JOIN user_pregnancy AS usp
      		ON nps.pregnancy_tag = usp.pregnancy_tag
      		WHERE usp.user_id = ?
      			AND n.id = nps.nutrient_id
      			AND (nps.possibility = ? OR nps.possibility = ?)
      	)
      	AND NOT EXISTS (
          	SELECT 1 
          	FROM check_condition AS nc 
          	INNER JOIN user_condition AS uc 
          	ON nc.condition_tag = uc.condition_tag 
          	WHERE uc.user_id = ?  
            	AND n.id = nc.nutrient_id
      	)
      	AND NOT EXISTS (
         	 SELECT 1 
          	FROM check_allergy_warning AS naw 
          	INNER JOIN user_allergy AS ua 
          	ON naw.allergy_tag = ua.allergy_tag 
          	WHERE ua.user_id = ? 
            	AND n.id = naw.nutrient_id
      	)
      	AND NOT EXISTS (
         	 SELECT 1 
          	FROM check_drug_interaction AS ndi 
          	INNER JOIN user_medicine AS um 
          	ON ndi.medicine_tag = um.medicine_tag 
          	WHERE um.user_id = ? 
            	AND n.id = ndi.nutrient_id
      	);
   			 """;

		List<MedicalResponseDTO.NutrientDTO> nutrientDTOList = jdbcTemplate.query(sql,
			new RowMapper<MedicalResponseDTO.NutrientDTO>() {
				@Override
				public MedicalResponseDTO.NutrientDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					Long nutrientId = rs.getLong("nutrient_id");
					String nutrientName = rs.getString("nutrient_name");
					String description = rs.getString("nutrient_details");

					return new MedicalResponseDTO.NutrientDTO(nutrientId, nutrientName, description);
				}
			},
			typeName.name(), userId, userId, Possibility.AMBIGUOUS.name(), Possibility.IMPOSSIBLE.name(), userId, userId, userId
		);

		return nutrientDTOList;
	}




}
