package org.kpmp;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

public class WebConfigTest {

	private WebConfig config;

	@Before
	public void setUp() throws Exception {
		config = new WebConfig();
	}

	@After
	public void tearDown() throws Exception {
		config = null;
	}

	@Test
	public void testAddCorsMappings() {
		CorsRegistry registry = mock(CorsRegistry.class);
		CorsRegistration registration = mock(CorsRegistration.class);
		when(registry.addMapping("/**")).thenReturn(registration);

		config.addCorsMappings(registry);

		verify(registry).addMapping("/**");
		verify(registration).allowedOrigins("*");
		verify(registration).allowedMethods("GET", "POST");
	}

}
