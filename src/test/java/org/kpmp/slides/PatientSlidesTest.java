package org.kpmp.slides;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PatientSlidesTest extends PatientSlides {

	private PatientSlides patient;

	@Before
	public void setUp() throws Exception {
		patient = new PatientSlides();
	}

	@After
	public void tearDown() throws Exception {
		patient = null;
	}

	@Test
	public void testSetKpmpId() {
		patient.setKpmpId("r5t3");

		assertEquals("r5t3", patient.getKpmpId());
	}

	@Test
	public void testSetLabel() {
		patient.setLabel("r5t3 (Pilot 1)");

		assertEquals("r5t3 (Pilot 1)", patient.getLabel());
	}

	@Test
	public void testSetSlides() {
		List<Slide> slides = Arrays.asList(mock(Slide.class), mock(Slide.class));

		patient.setSlides(slides);

		assertEquals(slides, patient.getSlides());
	}

}
