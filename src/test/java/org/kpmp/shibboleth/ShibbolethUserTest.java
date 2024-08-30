package org.kpmp.shibboleth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShibbolethUserTest {

	private ShibbolethUser testUser;

	@BeforeEach
	public void setUp() throws Exception {
		testUser = new ShibbolethUser();
	}

	@AfterEach
	public void tearDown() throws Exception {
		testUser = null;
	}

	@Test
	public void testSetFirstName() {
		testUser.setFirstName("Ziggy");
		assertEquals("Ziggy", testUser.getFirstName());
	}

	@Test
	public void testSetLastName() {
		testUser.setLastName("Stardust");
		assertEquals("Stardust", testUser.getLastName());
	}

	@Test
	public void testSetEmail() {
		testUser.setEmail("ziggy@mars.com");
		assertEquals("ziggy@mars.com", testUser.getEmail());
	}

	@Test
	public void testSetDisplayName() {
		testUser.setDisplayName("Space Oddity");
		assertEquals("Space Oddity", testUser.getDisplayName());
	}

	@Test
	public void testToString() {
		testUser.setDisplayName("Space Oddity");
		testUser.setFirstName("Ziggy");
		testUser.setLastName("Stardust");
		testUser.setEmail("ziggy@mars.com");
		testUser.setShibId("ziggy@mars.com");
		assertEquals("firstName: Ziggy" + ", lastName: Stardust" + ", displayName: Space Oddity"
				+ ", email: ziggy@mars.com, shibId: ziggy@mars.com", testUser.toString());
	}

}