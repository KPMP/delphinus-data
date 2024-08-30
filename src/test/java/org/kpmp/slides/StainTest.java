package org.kpmp.slides;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StainTest {

	private Stain stain;

	@BeforeEach
	public void setUp() throws Exception {
		stain = new Stain();
	}

	@AfterEach
	public void tearDown() throws Exception {
		stain = null;
	}

	@Test
	public void testSetId() {
		stain.setId("245");
		assertEquals("245", stain.getId());
	}

	@Test
	public void testSetType() {
		stain.setType("he");
		assertEquals("he", stain.getType());
	}

	@Test
	public void testSetTitle() {
		stain.setTitle("title");
		assertEquals("title", stain.getTitle());
	}

	@Test
	public void testSetDescription() {
		stain.setDescription("description");
		assertEquals("description", stain.getDescription());
	}

}
