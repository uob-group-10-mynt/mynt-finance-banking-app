package com.mynt.mynt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
//@ContextConfiguration(classes = MyntProjectApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MyntProjectApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String schema;

	@BeforeEach
	void setUp() {
		schema = "http://localhost:" +  String.valueOf(this.port) ;
	}

	@Test
	void contextLoads() {
	}

	@Test
	void testAuth() {
		String url = schema + "/api/v1/auth";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		String requestBody = "{\"username\":\"testuser\", \"password\":\"testpass\"}";
		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
//		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
//		assertThat(response.getBody()).isNotNull();
		System.out.println("\n\n=========================\n\n"+response);
	}

}
