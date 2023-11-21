package com.interlogica.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.interlogica.business.ValidatorSourceManager;
import com.interlogica.data.RowResult;

@RestController
public class ValidatorController {

	@Autowired
	private ValidatorSourceManager service;

	@PostMapping("/validateNumber")
	ResponseEntity<RowResult> validateNumber(@RequestBody String number) {
		return ResponseEntity.ok(service.validate(UUID.randomUUID().toString(), number));
	}

	@PostMapping("/consumeFile")
	public ResponseEntity<List<RowResult>> consumeFile(@RequestParam("file") MultipartFile file) {
		List<RowResult> result = new ArrayList<>();
		try {
			result = service.loadFile(file);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}

	}

}
