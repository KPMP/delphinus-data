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
	private PatientSlidesRepository patientRepo;
	private SlideService service;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		service = new SlideService(patientRepo);
	}

	@After
	public void tearDown() throws Exception {
		service = null;
	}

	@Test
	public void testGetSlidesForPatient() {
		PatientSlides patient = mock(PatientSlides.class);
		List<Slide> slides = Arrays.asList(mock(Slide.class));
		when(patient.getSlides()).thenReturn(slides);
		when(patientRepo.findByKpmpId("345")).thenReturn(patient);

		List<Slide> result = service.getSlidesForPatient("345");

		assertEquals(slides, result);
	}

	@Test
	public void testGetSlidesForPatient_whenNoSlides() {
		when(patientRepo.findByKpmpId("345")).thenReturn(null);

		List<Slide> result = service.getSlidesForPatient("345");

		assertEquals(Collections.emptyList(), result);
	}

}
