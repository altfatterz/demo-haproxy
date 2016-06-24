package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class DemoHaproxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoHaproxyApplication.class, args);
	}

	@RequestMapping("/")
	public Map<String, String> headers(HttpServletRequest request) {
		Map<String, String> result = new HashMap<>();

		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			result.put(headerName, request.getHeader(headerName));
		}

		return result;
	}
}
