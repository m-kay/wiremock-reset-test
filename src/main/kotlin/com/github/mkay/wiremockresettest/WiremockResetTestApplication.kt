package com.github.mkay.wiremockresettest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class WiremockResetTestApplication{

	@Bean
	fun restTemplate(restTemplateBuilder: RestTemplateBuilder) : RestTemplate {
		return restTemplateBuilder.build()
	}
}

fun main(args: Array<String>) {
	runApplication<WiremockResetTestApplication>(*args)
}
