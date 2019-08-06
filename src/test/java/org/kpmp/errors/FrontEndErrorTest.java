package org.kpmp.errors;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FrontEndErrorTest {

	private FrontEndError error;

	@Before
	public void setup() {
		error = new FrontEndError();
	}

	@After
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
