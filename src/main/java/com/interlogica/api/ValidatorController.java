package com.interlogica.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.interlogica.business.ValidatorSourceManager;
import com.interlogica.data.AfricanPhoneNumber;
import com.interlogica.data.RowResult;

@Controller
public class ValidatorController {

	@Autowired
	private ValidatorSourceManager service;

	@PostMapping("/validateNumber")
	public ResponseEntity<RowResult> validateNumber(@RequestParam String phone_number) {
		return ResponseEntity.ok(service.validate(UUID.randomUUID().toString(), phone_number));
	}

	@GetMapping("/")
	public String showForm(Model model) {
		AfricanPhoneNumber african_number = new AfricanPhoneNumber();
		model.addAttribute("african_number", african_number);

		return "validation_form";
	}

	@PostMapping("/validateNumberWeb")
	public String validateNumberWeb(Model model, @ModelAttribute("african_number") AfricanPhoneNumber number) {
		model.addAttribute("report", service.validate(UUID.randomUUID().toString(), number.getNumber()));

		return "validation_report";
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
