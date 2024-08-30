package org.kpmp.errors;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FrontEndErrorTest {

	private FrontEndError error;

	@BeforeEach
	public void setup() {
		error = new FrontEndError();
	}

	@AfterEach
	public void tearDown() {
		error = null;
	}

	@Test
	public void setError() {
		error.setError("error message");
		assertEquals("error message", error.getError());
	}

	@Test
	public void setStackTrace() throws Exception {
		error.setStackTrace("stack trace");
		assertEquals("stack trace", error.getStackTrace());
	}
}
