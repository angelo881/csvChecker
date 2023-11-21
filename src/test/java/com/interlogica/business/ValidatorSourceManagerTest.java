package com.interlogica.business;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.interlogica.data.PhoneNumberResult;
import com.interlogica.data.RowResult;
import com.interlogica.repository.PhoneRepository;

@ExtendWith(MockitoExtension.class)
public class ValidatorSourceManagerTest {

	@Mock
	private PhoneRepository repo;

	@InjectMocks
	private ValidatorSourceManager manager = new ValidatorSourceManager(repo);

	@Test
	public void testValidateValid() throws Exception {
		assertEquals(PhoneNumberResult.VALID, manager.validate("test", "27831234567").getNumberResult());

	}

	@Test
	public void testValidateCorrected() throws Exception {
		assertEquals(PhoneNumberResult.CORRECTED, manager.validate("test", "38431234567").getNumberResult());
	}

	@Test
	public void testValidateCorrected2() throws Exception {
		assertEquals(PhoneNumberResult.CORRECTED, manager.validate("test", "3843123456709554398").getNumberResult());

	}
	@Test
	public void testValidateInvalid() throws Exception {
		assertEquals(PhoneNumberResult.NOT_VALID, manager.validate("test", "dsa").getNumberResult());

	}
	@Test
	public void validFile() throws Exception {
		File file = new File("src/test/resources/source.csv");
		FileInputStream input = new FileInputStream(file);
		MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
				IOUtils.toByteArray(input));
		List<RowResult> res = manager.loadFile(multipartFile);
		assertEquals(7, res.size());
		assertEquals(1, res.stream().filter(t -> t.getNumberResult().equals(PhoneNumberResult.VALID))
				.collect(Collectors.toList()).size());
		assertEquals(4, res.stream().filter(t -> t.getNumberResult().equals(PhoneNumberResult.CORRECTED))
				.collect(Collectors.toList()).size());
		assertEquals(2, res.stream().filter(t -> t.getNumberResult().equals(PhoneNumberResult.NOT_VALID))
				.collect(Collectors.toList()).size());
	}
}
