package de.freerider.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.stereotype.Component;

import de.freerider.model.Customer;


@Component
class CustomerRepository implements CrudRepository<Customer, String> {
	
	private final IDGenerator idGen = new IDGenerator( "C", IDGenerator.IDTYPE.NUM, 6 );
	private HashMap<String,Customer> customerList = new HashMap<String,Customer> ();
	@Override
	
	public <S extends Customer> S save(S entity) throws IllegalArgumentException {
		if(entity == null) {
			throw new IllegalArgumentException();
		}else {
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
		Customer c;
		if(customerList.containsKey(entity.getId())) {
		c= customerList.remove(entity.getId());
		customerList.put(entity.getId(), entity);
		return (S) c;
		}
		customerList.put(entity.getId(), entity);
		return entity;
		}
	}

	@Override
	public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) throws IllegalArgumentException{
		if(entities  == null) {
			throw new IllegalArgumentException();
		}
		for (Customer s: entities) {
			save(s);
			}
		return entities;
	}

	@Override
	public Optional<Customer> findById(String id) throws IllegalArgumentException{
		if(id == null) throw new IllegalArgumentException();
		Customer c =customerList.get(id);
		if(c == null) { 
			Optional<Customer>x = Optional.empty();
			return x;
		}
		Optional<Customer> o = Optional.of(c);
		return o;
	}

	@Override
	public boolean existsById(String id) throws IllegalArgumentException{
		if(id == null) throw new IllegalArgumentException();
		boolean enthalten = customerList.containsKey(id);
		return enthalten;
	}

	@Override 
	public Iterable<Customer> findAll() {
		//return customerList.values();
		ArrayList <Customer> a = new ArrayList<>();
		for(Customer i: customerList.values()) {
			a.add(i);
		}
		
		return a;
	}

	@Override
	public Iterable<Customer> findAllById(Iterable<String> ids) throws IllegalArgumentException {
		if(ids  == null) {
			throw new IllegalArgumentException();
		}
		ArrayList <Customer> list = new ArrayList<Customer>();
		boolean test;
		for(String id: ids) {
			if(id == null) throw new IllegalArgumentException();
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
	public void deleteById(String id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(id == null)throw new IllegalArgumentException();
		customerList.remove(id);
		
	}

	@Override
	public void delete(Customer entity) throws IllegalArgumentException{
		if(entity == null|| entity.getId()==null) throw new IllegalArgumentException();
		customerList.remove(entity.getId(),entity);
		
	}

	@Override
	public void deleteAllById(Iterable<? extends String> ids) throws IllegalArgumentException{
		if(ids == null) {
			throw new IllegalArgumentException();
		}
		for( String id : ids) {
			if(id == null) {
				throw new IllegalArgumentException();
			}
			deleteById(id);
		}
		
	}

	@Override
	public void deleteAll(Iterable<? extends Customer> entities) throws IllegalArgumentException {
		if(entities  == null) {
			throw new IllegalArgumentException();
		}
		for( Customer c : entities) {
			if(c == null || c.getId()==null) {
				throw new IllegalArgumentException();
			}
			delete(c);
		}
		
	}

	@Override
	public void deleteAll() {
		customerList.clear();
		
	}


	
}
