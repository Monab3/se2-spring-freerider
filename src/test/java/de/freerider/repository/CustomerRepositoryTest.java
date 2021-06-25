package de.freerider.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.freerider.model.Customer;

@SpringBootTest
public class CustomerRepositoryTest {
	@Autowired
	CrudRepository<Customer, String> customerRepository;
// two sample customers
	private Customer mats;
	private Customer thomas;

	@BeforeEach
	public void setUpFirst() {
		mats = new Customer("Mats", "Becher", "monabecher@gmx.de");
		thomas = new Customer("Thomas", "Mueller", "ThomesM@hotmail.com");
	}

	@Test
	public void saveTest() {
		// leeres Repository bevor der Speicherung
		assertEquals(0, customerRepository.count());

		customerRepository.save(mats);
		customerRepository.save(thomas);
		assertEquals(2, customerRepository.count());
		Optional<Customer> o = Optional.of(mats);
		assertEquals(o, customerRepository.findById(mats.getId()));

		// null Id
		Customer tom = new Customer("Tom", "Bib", "tom@gmx.de");
		tom.setId(null);
		customerRepository.save(tom);
		assertEquals(tom.getId(), customerRepository.findById(tom.getId()).get().getId());
		assertNotNull(tom.getId());

		// nicht null Id
		String id = ("1234");
		Customer simone = new Customer("simone", "Bob", "simone@gmx.de");
		simone.setId(id);
		customerRepository.save(simone);
		assertEquals(id, customerRepository.findById(simone.getId()).get().getId());

		// null-Reference versuchen zu speichern
		assertThrows(IllegalArgumentException.class, () -> {
			customerRepository.save(null);
		});
		assertEquals(4, customerRepository.count());

		// 2 mal selbe Objekt speichern
		customerRepository.save(simone);
		assertEquals(4, customerRepository.count());

		// 2 mal verschiedene Objekte mit selber Id
		Customer hans = new Customer("hans", "tump", "tump@gmx.de");
		hans.setId(simone.getId());
		customerRepository.save(hans);
		assertEquals(4, customerRepository.count());
	}

	@Test
	public void saveAllTest() {
		customerRepository.deleteAll();
		ArrayList<Customer> list = new ArrayList<>();
		list.add(mats);
		list.add(thomas);
		customerRepository.saveAll(list);
		assertEquals(2, customerRepository.count());
		Optional<Customer> o = Optional.of(mats);
		assertEquals(o, customerRepository.findById(mats.getId()));

		// objekte mit null-Id speichern
		customerRepository.deleteAll();
		mats.setId(null);
		thomas.setId(null);
		list.clear();
		list.add(mats);
		list.add(thomas);
		customerRepository.saveAll(list);
		assertEquals(2, customerRepository.count());
		assertNotNull(customerRepository.findById(mats.getId()).get().getId());

		// Objekte mit vorgegebenerId
		customerRepository.deleteAll();
		mats.setId(null);
		mats.setId("1234");
		list.clear();
		list.add(mats);
		list.add(thomas);
		customerRepository.saveAll(list);
		assertEquals("1234", customerRepository.findById(mats.getId()).get().getId());
		assertEquals(2, customerRepository.count());

		// null Referencen Speichern
		customerRepository.deleteAll();
		list.clear();
		list.add(null);
		list.add(null);
		assertThrows(IllegalArgumentException.class, () -> {
			customerRepository.saveAll(list);
		});

		// 2 mal das selbe Objekt speichern
		list.clear();
		thomas.setId(null);
		thomas.setId("1234");
		mats.setId(null);
		mats.setId("1234");
		list.add(thomas);
		list.add(thomas);
		customerRepository.saveAll(list);
		assertEquals(customerRepository.count(), 1);

		// 2 Objekte mit der selben Id speichern
		list.add(mats);
		assertEquals(customerRepository.count(), 1);

	}

	@Test
	public void findByIdTest() {

		// regulärer Fall
		customerRepository.deleteAll();
		customerRepository.save(mats);
		customerRepository.save(thomas);
		assertEquals(mats, customerRepository.findById(mats.getId()).get());

		// nach nicht vorhandener Id suchen
		Optional<Customer> o = customerRepository.findById("123");
		assertTrue(o.isEmpty());

		// null Id übergeben
		assertThrows(IllegalArgumentException.class, () -> {
			customerRepository.findById(null);
		});
	}

	@Test
	public void findAllTest() {
		// leeres Repository
		customerRepository.deleteAll();
		Iterable<Customer> it = customerRepository.findAll();
		int i = 0;
		for (Customer c : it) {
			i++;
		}
		assertEquals(0, i);

		// ob alle gefunden werden
		customerRepository.save(mats);
		customerRepository.save(thomas);
		ArrayList<Customer> a = (ArrayList<Customer>) customerRepository.findAll();
		assertTrue(a.contains(mats));
		assertTrue(a.contains(thomas));

	}

	@Test
	public void findAllByIdTest() {
		customerRepository.deleteAll();
		customerRepository.save(mats);
		customerRepository.save(thomas);

		// vorhandenen Id's finden
		ArrayList<String> array = new ArrayList<>();
		array.add(mats.getId());
		array.add(thomas.getId());
		ArrayList<Customer> list = (ArrayList<Customer>) customerRepository.findAllById(array);
		assertTrue(list.contains(mats));
		assertTrue(list.contains(thomas));

		// nichtvorhande Id's suchen
		array.clear();
		array.add("12345");
		array.add("2345");
		list = (ArrayList<Customer>) customerRepository.findAllById(array);
		assertTrue(list.isEmpty());

		// nach vorhandener und nicht vorhandener Id suchen
		array.add(thomas.getId());
		list = (ArrayList<Customer>) customerRepository.findAllById(array);
		assertEquals(list.size(), 1);
		assertTrue(list.contains(thomas));

		// null Id's suchen
		array.clear();
		array.add(null);
		array.add(null);
		assertThrows(IllegalArgumentException.class, () -> {
			customerRepository.findAllById(array);
		});

		// 2 mal die selbe Id übergeben
		array.clear();
		array.add(mats.getId());
		array.add(mats.getId());
		list = (ArrayList<Customer>) customerRepository.findAllById(array);
		assertEquals(list.size(), 2);
	}

	@Test
	public void countTest() {
		assertEquals(2, customerRepository.count());
		customerRepository.deleteAll();
		assertEquals(0, customerRepository.count());
	}

	@Test
	public void deleteById() {
		customerRepository.deleteAll();
		customerRepository.save(mats);
		customerRepository.save(thomas);

		// vorhandenes Objekt löschen
		customerRepository.deleteById(mats.getId());
		assertFalse(customerRepository.existsById(mats.getId()));
		assertEquals(customerRepository.count(), 1);

		// nicht vorhandenes Objekt löschen
		customerRepository.deleteById("12");
		assertEquals(customerRepository.count(), 1);

		// null-Reference löschen
		assertThrows(IllegalArgumentException.class, () -> {
			customerRepository.deleteById(null);
		});
	}

	@Test
	public void deleteTest() {
		customerRepository.deleteAll();
		customerRepository.save(mats);
		customerRepository.save(thomas);

		// vorhandenes Objekt löschen
		customerRepository.delete(mats);
		assertFalse(customerRepository.existsById(mats.getId()));
		assertEquals(customerRepository.count(), 1);

		// nicht vorhandenes Objekt löschen
		Customer simone = new Customer("simone", "Bob", "simone@gmx.de");
		simone.setId("123");
		customerRepository.delete(simone);
		assertEquals(customerRepository.count(), 1);

		// null-Reference löschen
		assertThrows(IllegalArgumentException.class, () -> {
			customerRepository.delete(null);
		});

	}

	@Test
	public void deleteAllByIdTest() {
		customerRepository.deleteAll();
		customerRepository.save(mats);
		customerRepository.save(thomas);
		Customer simone = new Customer("simone", "Bob", "simone@gmx.de");
		customerRepository.save(simone);

		ArrayList<String> ids = new ArrayList<>();
		ids.add(mats.getId());
		ids.add(thomas.getId());

		// löschen vorhandener Objekte
		customerRepository.deleteAllById(ids);
		assertFalse(customerRepository.existsById(mats.getId()));
		assertFalse(customerRepository.existsById(thomas.getId()));
		assertEquals(customerRepository.count(), 1);

		// löschen vorhandener und nicht vorhandener Objekte
		customerRepository.save(thomas);
		customerRepository.deleteAllById(ids);
		assertFalse(customerRepository.existsById(mats.getId()));
		assertFalse(customerRepository.existsById(thomas.getId()));
		assertEquals(customerRepository.count(), 1);

		// löschen einer null liste
		assertThrows(IllegalArgumentException.class, () -> {
			customerRepository.deleteAllById(null);
		});

		// löschen einer Liste mit null reference
		customerRepository.save(mats);
		customerRepository.save(thomas);
		ids.add(null);
		assertThrows(IllegalArgumentException.class, () -> {
			customerRepository.deleteAllById(ids);
		});
	}

	@Test
	public void deleteAllTest() {
		customerRepository.deleteAll();
		customerRepository.save(mats);
		customerRepository.save(thomas);
		Customer simone = new Customer("simone", "Bob", "simone@gmx.de");
		customerRepository.save(simone);

		ArrayList<Customer> entities = new ArrayList<>();
		entities.add(mats);
		entities.add(thomas);

		// löschen vorhandener Objekte
		customerRepository.deleteAll(entities);
		assertFalse(customerRepository.existsById(mats.getId()));
		assertFalse(customerRepository.existsById(thomas.getId()));
		assertEquals(customerRepository.count(), 1);

		// löschen vorhandener und nicht vorhandener Objekte
		customerRepository.save(thomas);
		customerRepository.deleteAll(entities);
		assertFalse(customerRepository.existsById(mats.getId()));
		assertFalse(customerRepository.existsById(thomas.getId()));
		assertEquals(customerRepository.count(), 1);

		// löschen einer Null Reference
		customerRepository.save(mats);
		customerRepository.save(thomas);
		entities.add(null);
		assertThrows(IllegalArgumentException.class, () -> {
			customerRepository.deleteAll(entities);
		});

		// übergebene liste ist null
		assertThrows(IllegalArgumentException.class, () -> {
			customerRepository.deleteAll(null);
		});
	}

	@Test
	public void deleteAllOhneParameterTest() {
		customerRepository.deleteAll();
		customerRepository.save(mats);
		customerRepository.save(thomas);
		customerRepository.deleteAll();

		// liste ist leer
		assertEquals(customerRepository.count(), 0);
		assertFalse(customerRepository.existsById(mats.getId()));
		assertFalse(customerRepository.existsById(thomas.getId()));
	}
}
