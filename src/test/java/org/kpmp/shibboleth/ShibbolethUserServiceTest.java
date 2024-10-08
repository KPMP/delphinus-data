package org.kpmp.shibboleth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ShibbolethUserServiceTest {

	private ShibbolethUserService shibbolethUserService;
	@Mock
	private UTF8Encoder utf8Encoder;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		shibbolethUserService = new ShibbolethUserService(utf8Encoder);
	}

	@AfterEach
	public void tearDown() throws Exception {
		shibbolethUserService = null;
	}

	@Test
	public void testGetUser() throws UnsupportedEncodingException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("mail")).thenReturn("maninblack@jcash.com");
		when(request.getHeader("givenname")).thenReturn("Johnny");
		when(request.getHeader("sn")).thenReturn("Cash");
		when(request.getHeader("displayname")).thenReturn("Johnny Cash");
		when(request.getHeader("eppn")).thenReturn("shibId");
		when(utf8Encoder.convertFromLatin1("Johnny")).thenReturn("Johnny");
		when(utf8Encoder.convertFromLatin1("Cash")).thenReturn("Cash");
		when(utf8Encoder.convertFromLatin1("Johnny Cash")).thenReturn("Johnny Cash");
		when(utf8Encoder.convertFromLatin1("maninblack@jcash.com")).thenReturn("maninblack@jcash.com");
		when(utf8Encoder.convertFromLatin1("shibId")).thenReturn("shibId");

		ShibbolethUser user = shibbolethUserService.getUser(request);

		assertEquals("maninblack@jcash.com", user.getEmail());
		assertEquals("Johnny Cash", user.getDisplayName());
		assertEquals("Cash", user.getLastName());
		assertEquals("Johnny", user.getFirstName());
		assertEquals("shibId", user.getShibId());

	}

}