package org.kpmp.slides;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kpmp.logging.LoggingService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.http.HttpServletRequest;

public class SlideControllerTest {

	@Mock
	private SlideService slideService;
	@Mock
	private LoggingService loggingService;
	private SlideController controller;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		controller = new SlideController(slideService, loggingService);
	}

	@AfterEach
	public void tearDown() throws Exception {
		controller = null;
	}

	@Test
	public void testGetSlidesForParticipant() {
		List<Slide> slides = Arrays.asList(mock(Slide.class));
		Map<String, List<Slide>> slideMap = new HashMap<>();
		slideMap.put("(LM) Light Microscopy", slides);
		when(slideService.getSlidesForParticipant("444")).thenReturn(slideMap);
		HttpServletRequest request = mock(HttpServletRequest.class);
		Map<String, List<Slide>> result = controller.getSlidesForParticipant("444", request);
		verify(loggingService).logInfoMessage(controller.getClass(), "Getting slides for participant 444", request);
		assertEquals(slideMap, result);
	}

	@Test
	public void testGetAllSlides() {
		List<Participant> slides = Arrays.asList(mock(Participant.class));
		when(slideService.getAllParticipants()).thenReturn(slides);
		HttpServletRequest request = mock(HttpServletRequest.class);
		List<Participant> result = controller.getAllParticipants(request);
		verify(loggingService).logInfoMessage(controller.getClass(), "Getting all participants", request);
		assertEquals(slides, result);
	}

}
