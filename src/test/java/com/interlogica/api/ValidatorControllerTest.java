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
import org.springframework.web.multipart.MultipartFile;

import com.interlogica.business.ValidatorSourceManager;

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
