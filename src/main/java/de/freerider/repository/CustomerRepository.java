package de.freerider.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.springframework.stereotype.Component;

import de.freerider.model.Customer;


@Component
class CustomerRepository implements CrudRepository<Customer, String> {
	
	private final IDGenerator idGen = new IDGenerator( "C", IDGenerator.IDTYPE.NUM, 6 );
	private HashMap<String,Customer> customerList = new HashMap<String,Customer> ();
	@Override
	public <S extends Customer> S save(S entity) {
		String id;
		if(entity.getId() == null || entity.getId().equals("")) {
			while(true) {
			id = idGen.nextId();
			if(customerList.containsKey(id)==false) {
				break;
			}
		}
		entity.setId(id);
		}
		customerList.put(entity.getId(), entity);
		return entity;
	}

	@Override
	public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) {
		for (Customer s: entities) {
			save(s);
		}
		return entities;
	}

	@Override
	public Optional<Customer> findById(String id) {
		Customer c =customerList.get(id);
		Optional<Customer> o = Optional.of(c);
		return o;
	}

	@Override
	public boolean existsById(String id) {
		boolean enthalten = customerList.containsKey(id);
		return enthalten;
	}

	@Override 
	public Iterable<Customer> findAll() {
		ArrayList <Customer> a = new ArrayList<>();
		for(Customer i: customerList.values()) {
			a.add(i);
		}
		
		return a;
	}

	@Override
	public Iterable<Customer> findAllById(Iterable<String> ids) {
		ArrayList <Customer> list = new ArrayList<Customer>();
		boolean test;
		for(String id: ids) {
			test = customerList.containsKey(id);
			if(test) 
				list.add(customerList.get(id));
		}
		return list;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return customerList.size();
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		customerList.remove(id);
		
	}

	@Override
	public void delete(Customer entity) {
		customerList.remove(entity.getId(),entity);
		
	}

	@Override
	public void deleteAllById(Iterable<? extends String> ids) {
		for( String id : ids) {
			deleteById(id);
		}
		
	}

	@Override
	public void deleteAll(Iterable<? extends Customer> entities) {
		for( Customer c : entities) {
			delete(c);
		}
		
	}

	@Override
	public void deleteAll() {
		customerList.clear();
		
	}


	
}
