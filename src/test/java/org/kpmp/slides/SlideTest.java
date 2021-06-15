package org.kpmp.slides;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SlideTest {

	private Slide slide;

	@Before
	public void setUp() throws Exception {
		slide = new Slide();
	}

	@After
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

	@Test
	public void testSetAperio() {
		Aperio aperio = new Aperio();
		Metadata metadata = new Metadata();
		metadata.setAperio(aperio);

		assertEquals(aperio, metadata.getAperio());
	}
}
