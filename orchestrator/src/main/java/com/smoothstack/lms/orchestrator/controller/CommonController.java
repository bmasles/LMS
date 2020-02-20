package com.smoothstack.lms.orchestrator.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class CommonController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/administrator/**")
	public ResponseEntity<Object> handleAdministratorGetRequest(HttpServletRequest request) {
		try {
			return restTemplate.getForEntity(String.format("http://administrator-service%s", request.getRequestURI()),
					Object.class);
		} catch (HttpClientErrorException e) {
			return ResponseEntity.status(e.getStatusCode()).build();
		}
	}

	@PostMapping("/administrator/**")
	public ResponseEntity<Object> handleAdministratorPostRequest(@RequestBody Object object,
			HttpServletRequest request) {
		try {
			return restTemplate.postForEntity(String.format("http://administrator-service%s", request.getRequestURI()),
					object, Object.class);
		} catch (HttpClientErrorException e) {
			return ResponseEntity.status(e.getStatusCode()).build();
		}
	}

	@PutMapping("/administrator/**")
	public ResponseEntity<Object> handleAdministratorPutRequest(@RequestBody Object object,
			HttpServletRequest request) {
		try {
			return restTemplate.exchange(String.format("http://administrator-service%s", request.getRequestURI()),
					HttpMethod.valueOf(request.getMethod()), new HttpEntity<Object>(object), Object.class);
		} catch (HttpClientErrorException e) {
			return ResponseEntity.status(e.getStatusCode()).build();
		}
	}

	@DeleteMapping("/administrator/**")
	public ResponseEntity<Object> handleAdministratorDeleteRequest(HttpServletRequest request) {
		try {
			return restTemplate.exchange(String.format("http://administrator-service%s", request.getRequestURI()),
					HttpMethod.valueOf(request.getMethod()), new HttpEntity<Object>(null), Object.class);
		} catch (HttpClientErrorException e) {
			return ResponseEntity.status(e.getStatusCode()).build();
		}
	}

	@GetMapping("/librarian/**")
	public ResponseEntity<Object> handleLibrarianGetRequest(HttpServletRequest request) {
		try {
			return restTemplate.getForEntity(String.format("http://librarian-service%s", request.getRequestURI()),
					Object.class);
		} catch (HttpClientErrorException e) {
			return ResponseEntity.status(e.getStatusCode()).build();
		}
	}

	@PostMapping("/librarian/**")
	public ResponseEntity<Object> handleLibrarianPostRequest(@RequestBody Object object, HttpServletRequest request) {
		return restTemplate.postForEntity(String.format("http://librarian-service%s", request.getRequestURI()),
				new HttpEntity<Object>(object), Object.class);
	}

	@PutMapping("/librarian/**")
	public ResponseEntity<Object> handleLibrarianPutRequest(@RequestBody Object object, HttpServletRequest request) {
		return restTemplate.exchange(String.format("http://librarian-service%s", request.getRequestURI()),
				HttpMethod.PUT, new HttpEntity<Object>(object), Object.class);
	}

	@DeleteMapping("/librarian/**")
	public ResponseEntity<Object> handleLibrarianDeleteRequest(HttpServletRequest request) {
		return restTemplate.exchange(String.format("http://librarian-service%s", request.getRequestURI()),
				HttpMethod.DELETE, new HttpEntity<Object>(null), Object.class);
	}

	@GetMapping("/borrower/**")
	public ResponseEntity<Object> handleBorrowerGetRequest(HttpServletRequest request) {
		return restTemplate.getForEntity(String.format("http://borrower-service%s", request.getRequestURI()),
				Object.class);
	}

	@PostMapping("/borrower/**")
	public ResponseEntity<Object> handleBorrowerPostRequest(@RequestBody Object object, HttpServletRequest request) {
		return restTemplate.postForEntity(String.format("http://borrower-service%s", request.getRequestURI()), object,
				Object.class);
	}

	@PutMapping("/borrower/**")
	public ResponseEntity<Object> handleBorrowerPutRequest(@RequestBody Object object, HttpServletRequest request) {
		return restTemplate.exchange(String.format("http://borrower-service%s", request.getRequestURI()),
				HttpMethod.PUT, new HttpEntity<Object>(object), Object.class);
	}

	@DeleteMapping("/borrower/**")
	public ResponseEntity<Object> handleBorrowerDeleteRequest(HttpServletRequest request) {
		return restTemplate.exchange(String.format("http://borrower-service%s", request.getRequestURI()),
				HttpMethod.DELETE, new HttpEntity<Object>(null), Object.class);
	}

}
