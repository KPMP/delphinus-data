package org.kpmp.errors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kpmp.logging.LoggingService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ErrorControllerTest {

	@Mock
	private LoggingService logger;
	private ErrorController controller;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new ErrorController(logger);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLogError() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		FrontEndError error = new FrontEndError();
		error.setError("Oh noes...we had a front-end error");
		error.setStackTrace("stackTrace");

		controller.logError(error, request);

		verify(logger).logErrorMessage(ErrorController.class,
				"Oh noes...we had a front-end error with stacktrace: stackTrace", request);
	}

}
