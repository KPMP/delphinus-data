package org.kpmp.slides;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SlideControllerTest {

	@Mock
	private SlideService slideService;
	private SlideController controller;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new SlideController(slideService);
	}

	@After
	public void tearDown() throws Exception {
		controller = null;
	}

	@Test
	public void testGetSlidesForParticipant() {
		List<Slide> slides = Arrays.asList(mock(Slide.class));
		when(slideService.getSlidesForParticipant("444")).thenReturn(slides);

		List<Slide> result = controller.getSlidesForParticipant("444");

		assertEquals(slides, result);
	}

	@Test
	public void testGetAllSlides() {
		List<Participant> slides = Arrays.asList(mock(Participant.class));
		when(slideService.getAllParticipants()).thenReturn(slides);

		List<Participant> result = controller.getAllParticipants();
		assertEquals(slides, result);
	}

}
