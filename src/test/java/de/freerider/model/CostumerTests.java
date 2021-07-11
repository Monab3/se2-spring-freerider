package de.freerider.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.freerider.datamodel.Customer;

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
		de.freerider.datamodel.Status n = de.freerider.datamodel.Status.New;
		assertEquals(n, mats.getStatus());
	}

	@Test
	public void testSetStatus() {
		mats.setStatus(de.freerider.datamodel.Status.Active);
		assertEquals(de.freerider.datamodel.Status.Active, mats.getStatus());
		mats.setStatus(de.freerider.datamodel.Status.Deleted);
		assertEquals(de.freerider.datamodel.Status.Deleted, mats.getStatus());
		mats.setStatus(de.freerider.datamodel.Status.InRegistration);
		assertEquals(de.freerider.datamodel.Status.InRegistration, mats.getStatus());
		mats.setStatus(de.freerider.datamodel.Status.Suspended);
		assertEquals(de.freerider.datamodel.Status.Suspended, mats.getStatus());
		mats.setStatus(de.freerider.datamodel.Status.New);
		assertEquals(de.freerider.datamodel.Status.New, mats.getStatus());
	}
}
