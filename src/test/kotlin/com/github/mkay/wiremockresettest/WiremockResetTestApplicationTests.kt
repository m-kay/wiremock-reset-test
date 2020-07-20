package com.github.mkay.wiremockresettest

import com.github.tomakehurst.wiremock.WireMockServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.web.client.RestTemplate

@SpringBootTest
@AutoConfigureWireMock(port = 0, stubs = ["classpath:/wiremock/stubs"], files = ["classpath:/wiremock"])
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class WiremockResetTestApplicationTests {

	@Autowired
	private lateinit var wiremockServer: WireMockServer

	@Autowired
	private lateinit var restTemplate: RestTemplate

	@Value("\${wiremock.server.port}")
	private var wiremockPort: Long? = null

	@Test
	@Order(1)
	fun `stub is available`() {
		val response = restTemplate.getForEntity("http://localhost:${wiremockPort}/test-request", TestResponse::class.java)
		assertNotNull(response)
		assertThat(response.statusCode.value()).isEqualTo(200)
	}

	@Test
	@Order(2)
	fun `stub is not avaiable after reset`(){
		wiremockServer.resetToDefaultMappings()

		val response = restTemplate.getForEntity("http://localhost:${wiremockPort}/test-request", TestResponse::class.java)
		assertNotNull(response)
		assertThat(response.statusCode.value()).isEqualTo(200)
	}
}
