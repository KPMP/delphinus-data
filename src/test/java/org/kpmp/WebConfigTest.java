package org.kpmp;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

public class WebConfigTest {

	private WebConfig config;

	@BeforeEach
	public void setUp() throws Exception {
		config = new WebConfig();
	}

	@AfterEach
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
