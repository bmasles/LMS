package com.smoothstack.lms.orchestrator.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CommonController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/administrator/**")
	public ResponseEntity<Object> handleAdministratorRequest(HttpServletRequest request) {
		return restTemplate.getForEntity("http://administrator-service".concat(request.getRequestURI()), Object.class);
	}

	@GetMapping("/librarian/**")
	public ResponseEntity<Object> handleLibrarianRequest(HttpServletRequest request) {
		return restTemplate.getForEntity("http://librarian-service".concat(request.getRequestURI()), Object.class);
	}

	@GetMapping("/borrower/**")
	public ResponseEntity<Object> handleBorrowerRequest(HttpServletRequest request) {
		return restTemplate.getForEntity("http://borrower-service".concat(request.getRequestURI()), Object.class);
	}
}
