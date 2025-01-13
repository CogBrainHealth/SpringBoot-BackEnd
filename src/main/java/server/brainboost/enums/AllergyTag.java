package server.brainboost.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum AllergyTag {

	// 2번째 질문: 알레르기 여부
	UNKNOWN,            // 원인 알 수 없음
	PEANUT,             // 땅콩
	SHELLFISH,          // 갑각류
	ESTROGEN,           // 에스트로겐 민감
	LACQUER,            // 옻
	BARLEY,             // 소맥/보리
	CAFFEINE,           // 카페인 민감
	SPECIFIC,           // 특정 알레르기
	SOY,                // 대두
	POLLEN,             // 호박씨
	DAIRY,              // 유제품/유당불내증
	FIG,                // 무화과
	HOP_EXTRACT,        // 호프추출물
	NUT,                // 사상자
	MOUNTAIN_ASH,       // 산수유
	EVENING_PRIMROSE,   // 달맞이꽃종자유
	WHEAT,              // 밀
	EGG,                // 난황/계란
	PROPOLIS,           // 프로폴리스
	GINSENG,            // 홍삼
	CITRUS              // 국화과/쑥갓/카모마일/해바라기씨 등

}
