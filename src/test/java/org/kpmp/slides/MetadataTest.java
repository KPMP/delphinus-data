package org.kpmp.slides;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MetadataTest {

	private Metadata metadata;

	@Before
	public void setUp() throws Exception {
		metadata = new Metadata();
	}

	@After
	public void tearDown() throws Exception {
		metadata = null;
	}

	@Test
	public void testSetOverlay() {
		Overlay overlay = new Overlay();
		List<Overlay> expected = Arrays.asList(overlay);

		metadata.setOverlay(expected);

		assertEquals(expected, metadata.getOverlay());
	}

	@Test
	public void testSetOverlayLabel() throws Exception {

		List<String> labels = Arrays.asList("label1", "label2");

		metadata.setOverlayLabel(labels);

		assertEquals(labels, metadata.getOverlayLabel());

	}

}
