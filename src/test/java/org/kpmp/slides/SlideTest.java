package org.kpmp.slides;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SlideTest {

	private Slide slide;

	@BeforeEach
	public void setUp() throws Exception {
		slide = new Slide();
	}

	@AfterEach
	public void tearDown() throws Exception {
		slide = null;
	}

	@Test
	public void testSetStain() {
		Stain stain = new Stain();
		slide.setStain(stain);

		assertEquals(stain, slide.getStain());
	}

	@Test
	public void testSetSlideName() {
		slide.setSlideName("slideName");

		assertEquals("slideName", slide.getSlideName());
	}

	@Test
	public void testSetId() {
		slide.setId("54354");

		assertEquals("54354", slide.getId());
	}

	@Test
	public void testSetMetadata() {
		Metadata metadata = new Metadata();
		slide.setMetadata(metadata);

		assertEquals(metadata, slide.getMetadata());
	}

}
