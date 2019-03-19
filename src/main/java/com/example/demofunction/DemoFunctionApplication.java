package com.example.demofunction;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.util.Base64;
import java.util.function.Function;

@SpringBootApplication
public class DemoFunctionApplication {

	@Bean
	public Function<String, Boolean> catnotcat(CloudVisionTemplate visionTemplate) {
		return (in) -> {
			AnnotateImageResponse annotateImageResponse = visionTemplate.analyzeImage(
					new ByteArrayResource(Base64.getDecoder().decode(in)), Feature.Type.LABEL_DETECTION);
			return annotateImageResponse.getLabelAnnotationsList().stream()
					.anyMatch(entity ->
						entity.getDescription().equalsIgnoreCase("cat") &&
								entity.getScore() >= 0.90f
					);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoFunctionApplication.class, args);
	}

}
