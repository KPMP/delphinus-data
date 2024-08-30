package org.kpmp.slides;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SlideServiceTest {

	@Mock
	private ParticipantRepository participantRepository;
	private SlideService service;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		service = new SlideService(participantRepository);
	}

	@AfterEach
	public void tearDown() throws Exception {
		service = null;
	}

	@Test
	public void testGetSlidesForParticipant() {
		Participant participant = mock(Participant.class);
		List<Slide> slides = Arrays.asList(mock(Slide.class));
		List<Slide> slides2 = Arrays.asList(mock(Slide.class));

		Map<String, List<Slide>> slideMap = new HashMap<>();
		slideMap.put("(LM) Light Microscopy", slides);
		slideMap.put("(EM) Electron Microscopy", slides2);

		when(participant.getSlides()).thenReturn(slideMap);
		when(participantRepository.findByKpmpId("345")).thenReturn(participant);

		Map<String, List<Slide>> result = service.getSlidesForParticipant("345");

		assertEquals(slideMap, result);
	}

	@Test
	public void testGetSlidesForParticipant_whenNoSlides() {
		when(participantRepository.findByKpmpId("345")).thenReturn(null);

		Map<String, List<Slide>> result = service.getSlidesForParticipant("345");

		assertEquals(Collections.emptyMap(), result);
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
