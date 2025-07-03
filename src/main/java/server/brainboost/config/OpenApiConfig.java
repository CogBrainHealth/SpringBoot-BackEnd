package server.brainboost.config;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenApiCustomiser loginResponseCustomizer() {
        return openApi -> {
            // 1) "/login" 경로의 POST Operation 가져오기
            PathItem loginPath = openApi.getPaths().get("/login");
            if (loginPath == null) return;
            Operation post = loginPath.getPost();
            if (post == null)    return;

            // 2) 기존 403 응답 등 모두 지우고
            post.getResponses().clear();

            // 3) 200 OK 응답 정의
            post.getResponses()
                    .addApiResponse("200", new ApiResponse()
                            .description("로그인 성공")
                            .content(new Content().addMediaType(
                                    "application/json",  // 상수 대신 직접 문자열
                                    new io.swagger.v3.oas.models.media.MediaType()
                                            .schema(new Schema<>().$ref("#/components/schemas/ApiResponse"))
                            ))
                    );

            // 4) 401 Unauthorized 응답 정의
            post.getResponses()
                    .addApiResponse("401", new ApiResponse()
                            .description("인증 실패 (토큰 필요 또는 유효하지 않음)")
                            .content(new Content().addMediaType(
                                    "application/json",  // 상수 대신 직접 문자열
                                    new io.swagger.v3.oas.models.media.MediaType()
                                            .schema(new Schema<>().$ref("#/components/schemas/ApiResponse"))
                            ))
                    );
        };
    }
}