package com.interlogica.business;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.interlogica.data.AfricanPhoneNumber;
import com.interlogica.data.PhoneNumber;
import com.interlogica.data.PhoneNumberResult;
import com.interlogica.data.RowResult;
import com.interlogica.repository.PhoneRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

@Component
public class ValidatorSourceManager {

	static Logger logger = LoggerFactory.getLogger(ValidatorSourceManager.class);

	private final static String PREFIX = "278";
	private final static int LENGTH = 11;

	private PhoneRepository repo;

	private Validator validator;

	public ValidatorSourceManager(PhoneRepository repo) {
		this.repo = repo;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
	}

	public List<RowResult> loadFile(MultipartFile file) throws IOException {
		List<RowResult> result = new ArrayList<>();

		List<String[]> rows = null;
		File fileO = File.createTempFile("data", "temp");
		file.transferTo(fileO);
		try (Reader reader = Files.newBufferedReader(fileO.toPath())) {
			try (CSVReader csvReader = new CSVReader(reader)) {
				rows = csvReader.readAll();

			}
		} catch (IOException | CsvException e) {
			logger.warn("error in parse file", e);
			throw new RuntimeException("error in source file");
		}
		//rimuovo header file
		rows.remove(0);
		for (String[] row : rows) {
			RowResult singleResult = validate(row[0], row[1]);
			if (singleResult.getNumberResult().equals(PhoneNumberResult.VALID)
					|| singleResult.getNumberResult().equals(PhoneNumberResult.CORRECTED)) {
				repo.save(new PhoneNumber(singleResult.getId(), singleResult.getNumber()));
				logger.info("numero {} salvato correttamente", row[1]);
			}
			result.add(singleResult);

		}
		return result;
	}

	public RowResult validate(String id, String number) {
		RowResult result = new RowResult(id, number);
		if (!validateNumber(number)) {
			result.setNumberResult(PhoneNumberResult.NOT_VALID);
			if (number.length() >= LENGTH) {
				String new_number = PREFIX + number.substring(PREFIX.length(),LENGTH);
				if (validateNumber(new_number)) {
					result.setNumber(new_number);
					result.setNote(new StringBuffer().append("number ").append(number).append(" corrected in ")
							.append(new_number).toString());
					result.setNumberResult(PhoneNumberResult.CORRECTED);
				}
			}
		}
		return result;

	}

	private boolean validateNumber(String a) {
		Set<ConstraintViolation<AfricanPhoneNumber>> violations = validator.validate(new AfricanPhoneNumber(a));
		if (!violations.isEmpty()) {
			return false;
		}
		return true;
	}
}
