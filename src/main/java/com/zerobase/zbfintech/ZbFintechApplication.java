package com.zerobase.zbfintech;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@ServletComponentScan
@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(
		info = @Info(
				title = "zerobase fintech 개인프로젝트 ",  // 변경할 API 제목
				version = "1.0.0",
				description = "zerbase에서 fintech를 주제로 진행한 개인프로젝트입니다."
		)
)
public class ZbFintechApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZbFintechApplication.class, args);
	}

}
