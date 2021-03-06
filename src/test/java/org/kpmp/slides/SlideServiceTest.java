package org.kpmp.slides;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SlideServiceTest {

	@Mock
	private ParticipantRepository participantRepository;
	private SlideService service;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		service = new SlideService(participantRepository);
	}

	@After
	public void tearDown() throws Exception {
		service = null;
	}

	@Test
	public void testGetSlidesForParticipant() {
		Participant participant = mock(Participant.class);
		List<Slide> slides = Arrays.asList(mock(Slide.class));
		when(participant.getSlides()).thenReturn(slides);
		when(participantRepository.findByKpmpId("345")).thenReturn(participant);

		List<Slide> result = service.getSlidesForParticipant("345");

		assertEquals(slides, result);
	}

	@Test
	public void testGetSlidesForParticipant_whenNoSlides() {
		when(participantRepository.findByKpmpId("345")).thenReturn(null);

		List<Slide> result = service.getSlidesForParticipant("345");

		assertEquals(Collections.emptyList(), result);
	}

	@Test
	public void testGetAllParticipantSlides() {
		List<Participant> participantSlidesList = Arrays.asList(mock(Participant.class));
		when(participantRepository.findByOrderByKpmpIdAsc()).thenReturn(participantSlidesList);
		List<Participant> result = service.getAllParticipants();
		assertEquals(participantSlidesList, result);
	}

	@Test
	public void testGetAllParticipantSlides_noResults() {
		when(participantRepository.findByOrderByKpmpIdAsc()).thenReturn(null);
		List<Participant> result = service.getAllParticipants();
		assertEquals(Collections.emptyList(), result);
	}

}
