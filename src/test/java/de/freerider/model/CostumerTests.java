package de.freerider.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.freerider.model.Customer;

public class CostumerTests {
	private Customer mats;
	private Customer thomas;

	@BeforeEach
	public void setUp() {
		mats = new Customer("Mats", "Becher", "monabecher@gmx.de");
		thomas = new Customer("Thomas", "Mueller", "ThomesM@hotmail.com");
	}

	@Test
	public void testIdNull() {
		assertNull(mats.getId());
	}

	@Test
	public void testSetId() {
		mats.setId("12345");
		assertEquals("12345", mats.getId());
		thomas.setId("");
		assertNotNull(thomas.getId());
		thomas.setId(null);
		assertNull(thomas.getId());
	}

	@Test
	public void testSetIdOnlyOnce() {
		mats.setId("12345");
		mats.setId("123");
		assertNotEquals(mats.getId(), "123");
	}

	@Test
	public void testResetId() {
		mats.setId("12345");
		mats.setId(null);
		assertNull(mats.getId());
	}

	@Test
	public void testNamesInitial() {
		assertNotNull(mats.getLastName());
		assertNotNull(mats.getFirstName());
	}

	@Test
	public void testNamesSetNull() {
		mats.setFirstName(null);
		mats.setLastName(null);
		assertEquals(mats.getLastName(), "");
		assertEquals(mats.getFirstName(), "");
	}

	@Test
	public void testSetNames() {
		mats.setFirstName("Moritz");
		mats.setLastName("Schuster");
		assertEquals(mats.getFirstName(), "Moritz");
		assertEquals(mats.getLastName(), "Schuster");
	}

	@Test
	public void testContactsInitial() {
		assertNotNull(mats.getContact());
		assertNotNull(mats.getContact());
	}

	@Test
	public void testContactsSetNull() {
		mats.setContact(null);
		assertEquals(mats.getContact(), "");
	}

	@Test
	public void testSetContact() {
		mats.setContact("mats@h.de");
		assertEquals(mats.getContact(), "mats@h.de");

	}

	@Test
	public void testStatusInitial() {
		de.freerider.model.Status n = de.freerider.model.Status.NEW;
		assertEquals(n, mats.getStatus());
	}

	@Test
	public void testSetStatus() {
		mats.setStatus(de.freerider.model.Status.ACTIVE);
		assertEquals(de.freerider.model.Status.ACTIVE, mats.getStatus());
		mats.setStatus(de.freerider.model.Status.DELETED);
		assertEquals(de.freerider.model.Status.DELETED, mats.getStatus());
		mats.setStatus(de.freerider.model.Status.INREGISTRATION);
		assertEquals(de.freerider.model.Status.INREGISTRATION, mats.getStatus());
		mats.setStatus(de.freerider.model.Status.SUSPENDED);
		assertEquals(de.freerider.model.Status.SUSPENDED, mats.getStatus());
		mats.setStatus(de.freerider.model.Status.NEW);
		assertEquals(de.freerider.model.Status.NEW, mats.getStatus());
	}
}
