package org.kpmp.slides;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParticipantTest extends Participant {

	private Participant participant;

	@Before
	public void setUp() throws Exception {
		participant = new Participant();
	}

	@After
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

		participant.setSlides(slides);

		assertEquals(slides, participant.getSlides());
	}

}
