package de.freerider;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import de.freerider.model.Customer;
import de.freerider.repository.CrudRepository;

@SpringBootApplication
public class Se2SpringFreeriderApplication {
	@Autowired
	CrudRepository<Customer,String> customerRepository;
	
	@Bean
	CommandLineRunner runner() {
		return args -> {
			
        System.out.println("hello main");
        Customer mcEllen= new Customer("McEllen", "Mona","monamcellen@gmx.de" );
        Customer mcPellen= new Customer("McPellen", "Pona","ponamcpellen@gmx.de" );
        Customer ellen= new Customer("Ellen", "Tona","monaellen@gmx.de" );
        Customer schmidt= new Customer("Schmidt", "Tom","schmidt@gmail.de" );
        Customer pomergrante= new Customer("Pomergrante", "Simone","simone@hotmail.com");
        long count = customerRepository.count();
        Customer[]liste = {mcEllen,mcPellen,ellen,schmidt,pomergrante};
        System.out.println("hello main: "+ count);
      
        //<S extends Customer> S save(S entity) 
        for(int i = 0;i<liste.length;i++) {                                                                                                                                            
        	customerRepository.save(liste[i]);
        }
        //long count() 
        System.out.println("count funktioniert: 5 = "+ customerRepository.count());
        
        //aufgabe 8 - Anfang
        for(int i = 0;i<liste.length;i++) {                                                                                                                                            
        	customerRepository.save(liste[i]);
        }
        System.out.println("Aufgabe 8 anzahl von count 5 = "+customerRepository.count());
        //Ende von Aufgabe 8
        
        
        //findAll()
        Iterable<Customer> findAll  =customerRepository.findAll();
        for(Object customer: findAll ) {
        	System.out.println(customer.toString());
        }
        
        //deleteAll(Iterable<? extends Customer> entities)
        customerRepository.deleteAll(findAll);
        System.out.println("deleteAll(Iterable<? extends Customer> entities) :"+ customerRepository.count());
        
        //saveAll
        customerRepository.saveAll(findAll);
        System.out.println("SaveAll :"+ customerRepository.count());
        
        //delete()
        customerRepository.delete(mcEllen);
        System.out.println("delete :"+ customerRepository.count());
        
        //deleteById(String id)
        customerRepository.deleteById(mcPellen.getId());
        System.out.println("deleteByID :"+ customerRepository.count());
        
        //findAllByIds
        ArrayList<String>ids = new ArrayList<>();
        ids.add(ellen.getId());
        ids.add(schmidt.getId());
        Iterable<Customer> findAllById = customerRepository.findAllById(ids);
        System.out.println("FindAllById:");
        for(Customer customer: findAllById ) {
        	System.out.println(customer.getFirstName());
        }
        
      //deleteAllById(Iterable<? extends String> ids)
        customerRepository.deleteAllById(ids);
        System.out.println("deleteAllById: "+customerRepository.count());
        
        //existsById(String id)
        System.out.println("Pomergrante exsistsById: "+ customerRepository.existsById(pomergrante.getId()));
       
        //Optional<Customer> findById(String id)
        Optional<Customer>findByid = customerRepository.findById(pomergrante.getId());
        Optional<Customer>pom = Optional.of(pomergrante);
        System.out.println("optional<Customer>finxById works: "+findByid.equals(pom));

        // deleteAll() 
        customerRepository.deleteAll();
        System.out.println("deleteAll: "+customerRepository.count());
        
        
        
        
	};
	
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Se2SpringFreeriderApplication.class, args);
		
	}

}
