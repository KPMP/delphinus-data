package org.kpmp.slides;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OverlayTest {

	private Overlay overlay;

	@BeforeEach
	public void setUp() throws Exception {
		overlay = new Overlay();
	}

	@AfterEach
	public void tearDown() throws Exception {
		overlay = null;
	}

	@Test
	public void testSetPx() {
		overlay.setPx(12.3d);
		assertEquals(12.3d, overlay.getPx(), 0.0001);
	}

	@Test
	public void testSetPy() {
		overlay.setPy(22.2d);
		assertEquals(22.2d, overlay.getPy(), 0.0001);
	}

	@Test
	public void testSetWidth() {
		overlay.setWidth(13d);
		assertEquals(13d, overlay.getWidth(), 0.0001);
	}

	@Test
	public void testSetHeight() {
		overlay.setHeight(55d);
		assertEquals(55d, overlay.getHeight(), 0.0001);
	}

	@Test
	public void testSetClassName() {
		overlay.setClassName("class name");
		assertEquals("class name", overlay.getClassName());
	}

	@Test
	public void testSetId() {
		overlay.setId("overlay id");
		assertEquals("overlay id", overlay.getId());
	}

}
