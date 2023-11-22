package com.interlogica.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.interlogica.business.ValidatorSourceManager;
import com.interlogica.data.AfricanPhoneNumber;

@ExtendWith(MockitoExtension.class)
public class ValidatorControllerTest {
	@Mock
	private ValidatorSourceManager service;

	@InjectMocks
	private ValidatorController controller = new ValidatorController();

	@Test
	public void testValidateNumber() {
		String number = "32231232";
		ResponseEntity<?> resp = controller.validateNumber(number);
		Mockito.verify(service).validate(Mockito.anyString(), Mockito.eq(number));
		assertEquals(HttpStatus.OK, resp.getStatusCode());
	}

	@Test
	public void testValidateWeb() {
		String number = "32231232";
		controller.validateNumberWeb(Mockito.mock(Model.class), new AfricanPhoneNumber(number));
		Mockito.verify(service).validate(Mockito.anyString(), Mockito.eq(number));
	}

	@Test
	public void testShowForm() {
		String res = controller.showForm(Mockito.mock(Model.class));
		assertEquals("validation_form", res);
	}

	@Test
	public void testLoadFile() throws IOException {
		MultipartFile mock = Mockito.mock(MultipartFile.class);
		ResponseEntity<?> resp = controller.consumeFile(mock);
		Mockito.verify(service).loadFile(mock);
		assertEquals(HttpStatus.OK, resp.getStatusCode());
	}

	@Test
	public void testLoadFileKO() throws IOException {
		MultipartFile mock = Mockito.mock(MultipartFile.class);
		Mockito.when(service.loadFile(mock)).thenThrow(RuntimeException.class);
		ResponseEntity<?> resp = controller.consumeFile(mock);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());
	}
}
