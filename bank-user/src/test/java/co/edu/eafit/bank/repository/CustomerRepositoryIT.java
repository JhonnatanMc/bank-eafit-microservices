package co.edu.eafit.bank.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.eafit.bank.domain.Customer;
import co.edu.eafit.bank.domain.DocumentType;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@TestMethodOrder(OrderAnnotation.class)
class CustomerRepositoryIT {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	DocumentTypeRepository documentTypeRepository; 
	
	@Test
	@Order(1)
	void debeCrearUnCustomer() {
		//Arrange
		Integer idDocumentType=1;
		Integer idCustomer=14505050;
		
		Customer customer=null;
		DocumentType documentType=null;
		
		Optional<DocumentType> documentTypeOptional=documentTypeRepository.findById(idDocumentType);
		documentType=documentTypeOptional.get();
		
		customer=new Customer();
		customer.setAddress("Avenida Siempre Viva 123");
		customer.setCustId(idCustomer);
		customer.setDocumentType(documentType);
		customer.setEmail("hjsimpson@gmail.com");
		customer.setEnable("Y");
		customer.setName("Homero J Simpson");
		customer.setPhone("555 555 555");
		customer.setToken("328974kjaseiureyjkcsz");
		
		//Act
			customer=customerRepository.save(customer);
			
		//Assert
			assertNotNull(customer,"El customer es nulo no se grabo");
		
	}
	
	@Test
	@Order(2)
	void debeModificarUnCustomer() {
		//Arrange
		Integer idCustomer=14505050;
		
		Customer customer=null;
		
		Optional<Customer> customerOptional=null;	
		customerOptional=customerRepository.findById(idCustomer);
		
		if(customerOptional.isEmpty()==true) {
			throw new RuntimeException("El customer no existe");
		}
		String nameExpected="Homero J Simpson Gomez";
		
		customer=customerOptional.get();
		customer.setName("Homero J Simpson Gomez");
	
		
		//Act
			customer=customerRepository.save(customer);
			
		//Assert
			assertNotNull(customer,"El customer es nulo no se grabo");
			assertEquals(nameExpected, customer.getName());
		
	}
	
	@Test
	@Order(3)
	void debeBorrarUnCustomer() {
		//Arrange
		Integer idCustomer=14505050;
		Customer customer=null;
		Optional<Customer> customerOptional=null;	
		customerOptional=customerRepository.findById(idCustomer);
		
		if(customerOptional.isEmpty()==true) {
			throw new RuntimeException("El customer no existe");
		}
		
		
		customer=customerOptional.get();
		
		//Act
			customerRepository.delete(customer);
			
		//Assert
		
	}
	
	@Test
	@Order(4)
	void debeConsultarUnCustomerPorId() {
		
		//Arrange
			Customer customer=null;
			Optional<Customer> customerOptional=null;
			Integer id=1;
		
		//Act
			
			customerOptional=customerRepository.findById(id);
		
		//Assert
			assertTrue(customerOptional.isPresent(),"El customer con id:+"+id+" No existe");
			customer=customerOptional.get();
			log.info(customer.getName());

	}

	@Test
	@Order(5)
	void debeConsultarUnaListaDeClientes() {
		
		//Arrange
			List<Customer> customers=null;	
		
		//Act
			customers=customerRepository.findAll();
		
		//Assert
			assertNotNull(customers);
			assertFalse(customers.isEmpty());

	}
	
	

}
