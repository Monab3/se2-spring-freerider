package de.freerider.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

import de.freerider.model.Customer;

public class testCustomerKlasse {
	public static void main (String[] args) {
		
Customer mcEllen= new Customer("McEllen", "Mona","monamcellen@gmx.de" );
Customer mcPellen= new Customer("McPellen", "Pona","ponamcpellen@gmx.de" );
Customer ellen= new Customer("Ellen", "ona","monaellen@gmx.de" );
Customer schmidt= new Customer("Schmidt", "Tom","schmidt@gmail.de" );
Customer pomergrante= new Customer("Pomergrante", "Simone","simone@hotmail.com");

Customer[]liste = {mcEllen,mcPellen,ellen,schmidt,pomergrante};
CustomerRepository customerRepository = new CustomerRepository();

//<S extends Customer> S save(S entity) 
for(int i = 0;i<liste.length;i++) {
	System.out.println(liste[0]);                                                                                                                                              
	customerRepository.save(mcEllen);
}
//long count() 
System.out.println("count funktioniert: "+ customerRepository.count());

//Iterable<Customer> findAll() 
Iterable<Customer> c = customerRepository.findAll();
Customer[] wichtigfuerId = new Customer[6];
int i = 0;
for(Customer c2 : c ) {
	wichtigfuerId[i]=c2;
	i++;
	System.out.println("FindAll funktionier - Vorname: "+c2.getFirstName()+" Nachname: " +c2.getLastName()+" Contact: "+ c2.getContact());
}

//boolean existsById(String id) 
System.out.println("existsByid funktioniert: "+customerRepository.existsById(wichtigfuerId[0].getId()));

//Optional<Customer> findById(String id) 
Optional <Customer> o =customerRepository.findById(wichtigfuerId[0].getId()) ;
System.out.println("findById hat funktionier: "+ o.equals(wichtigfuerId[0]));


//void deleteById(String id) 
customerRepository.deleteById(wichtigfuerId[0].getId());
Optional <Customer> o2 =customerRepository.findById(wichtigfuerId[0].getId()) ;
System.out.println("deleteById hat funktioniert: ");
try {
	o.get();
}catch(NoSuchElementException e) {
	System.out.println(e.getMessage());
}
if(customerRepository.count()==4) {
	System.out.println("deleteByID hat funktionier!");
}

//void delete(Customer entity) 
customerRepository.delete(wichtigfuerId[1]);
System.out.println("delete funktioniert:"+ customerRepository.count()+" Onjekt exisitiert noch? "+ customerRepository.existsById(wichtigfuerId[1].getId()));


//void deleteAll() 
customerRepository.deleteAll();
System.out.println("DeleteAll hat funktioniert: "+ customerRepository.count());

//<S extends Customer> Iterable<S> saveAll(Iterable<S> entities) 
customerRepository.saveAll(c);
System.out.println("saveAll funktioniert: "+customerRepository.count());

Iterable<Customer> c4 = customerRepository.findAll();
Customer[] wichtigfuerId2 = new Customer[5];
int i4 = 0;
for(Customer c2 : c4) {
	wichtigfuerId2[i4]=c2;
	i4++;
}

//Iterable<Customer> findAllById(Iterable<String> ids)
Iterable<String> c3 = ids(wichtigfuerId2);
Iterable<Customer> findAllById = customerRepository.findAllById(c3);
System.out.println("find all by Id funktioniert");
for(Customer i1: findAllById) {
	System.out.println(i1);
}
//void deleteAllById(Iterable<? extends String> ids) 
Iterable<String> c5 = idDeleteById(wichtigfuerId2);
customerRepository.deleteAllById(c5);
System.out.println("Delete All By Id funktioniert: "+ customerRepository.count());
}
	public static Iterable<String> ids(Customer[]c){
		ArrayList<String>a = new ArrayList<>();
		for(int i = 0; i< c.length; i++) {
			a.add(c[i].getId());
		}
		return a;
	}
	
	public static Iterable<String> idDeleteById(Customer[]c){
		ArrayList<String>a = new ArrayList<>();
		for(int i = 0; i< c.length-2; i++) {
			a.add(c[i].getId());
		}
		return a;
	}
}