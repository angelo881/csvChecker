package com.interlogica.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;

@Controller
public class ValidatorController {

	
	static Logger logger = LoggerFactory.getLogger(ValidatorController.class);

	@Autowired
	private ValidatorSourceManager service;

	@ApiOperation(value = "Valida il numero telefonico", notes = "Questo servizio valida il numero telefonico, uso esterno")
	@PostMapping("/validateNumber")
	public ResponseEntity<RowResult> validateNumber(@RequestParam @Parameter(name = "phone_number", description = "Numero da validare") String phone_number) {
		logger.info("ricevuta richiesta validazione numero {}", phone_number);
		return ResponseEntity.ok(service.validate(UUID.randomUUID().toString(), phone_number));
	}

	
	@ApiOperation(value = "Form validazione numero telefonico", notes = "Questo servizio restituisce la form per inserimento numero telefonico")
	@GetMapping("/")
	public String showForm(Model model) {
		AfricanPhoneNumber african_number = new AfricanPhoneNumber();
		model.addAttribute("african_number", african_number);

		return "validation_form";
	}

	@ApiOperation(value = "Valida il numero telefonico", notes = "Questo servizio valida il numero telefonico, uso interno alla form")
	@PostMapping("/validateNumberWeb")
	public String validateNumberWeb(Model model, @ModelAttribute("african_number") AfricanPhoneNumber number) {
		model.addAttribute("report", service.validate(UUID.randomUUID().toString(), number.getNumber()));

		return "validation_report";
	}

	@ApiOperation(value = "Consuma il file csv", notes = "Questo servizio consente di importare un file .csv di numeri e restituisce un report in formato JSON")
	@PostMapping("/consumeFile")
	public ResponseEntity<List<RowResult>> consumeFile(@RequestParam("file") @Parameter(name = "file", description = "File da importare") MultipartFile file) {
		List<RowResult> result = new ArrayList<>();
		try {
			result = service.loadFile(file);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}

	}

}
