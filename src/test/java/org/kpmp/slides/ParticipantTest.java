package org.kpmp.slides;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParticipantTest extends Participant {

	private Participant participant;

	@BeforeEach
	public void setUp() throws Exception {
		participant = new Participant();
	}

	@AfterEach
	public void tearDown() throws Exception {
		participant = null;
	}

	@Test
	public void testSetKpmpId() {
		participant.setKpmpId("r5t3");

		assertEquals("r5t3", participant.getKpmpId());
	}

	@Test
	public void testSetLabel() {
		participant.setLabel("r5t3 (Pilot 1)");

		assertEquals("r5t3 (Pilot 1)", participant.getLabel());
	}

	@Test
	public void testSetSlides() {
		List<Slide> slides = Arrays.asList(mock(Slide.class), mock(Slide.class));
		Map<String, List<Slide>> slideMap = new HashMap<>();
		slideMap.put("(LM) Light Microscopy", slides);

		participant.setSlides(slideMap);

		assertEquals(slideMap, participant.getSlides());
	}

}
