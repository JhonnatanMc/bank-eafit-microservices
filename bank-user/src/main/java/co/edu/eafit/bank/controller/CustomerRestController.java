package co.edu.eafit.bank.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.eafit.bank.domain.Customer;
import co.edu.eafit.bank.dto.CustomerDTO;
import co.edu.eafit.bank.service.CustomerService;
import co.edu.eafit.bank.mapper.CustomerMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 *
 */
@RestController
@RequestMapping("/api/v1/customer")
@Slf4j
public class CustomerRestController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerMapper customerMapper;

	@GetMapping(value = "/{custId}")
	public ResponseEntity<CustomerDTO> findById(@PathVariable("custId") Integer custId) throws Exception {
		log.debug("Request to findById() Customer");

		Optional<Customer> optionalCustomer = customerService.findById(custId);

		Customer customer = (optionalCustomer.isPresent() == true) ? optionalCustomer.get() : null;

		return ResponseEntity.ok().body(customerMapper.customerToCustomerDTO(customer));
	}

	@GetMapping()
	public ResponseEntity<List<CustomerDTO>> findAll() throws Exception {
		log.debug("Request to findAll() Customer");

		return ResponseEntity.ok().body(customerMapper.listCustomerToListCustomerDTO(customerService.findAll()));
	}

	@PostMapping()
	public ResponseEntity<CustomerDTO> save(@Valid @RequestBody CustomerDTO customerDTO) throws Exception {
		log.debug("Request to save Customer: {}", customerDTO);

		Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
		customer = customerService.save(customer);

		return ResponseEntity.ok().body(customerMapper.customerToCustomerDTO(customer));
	}

	@PutMapping()
	public ResponseEntity<CustomerDTO> update(@Valid @RequestBody CustomerDTO customerDTO) throws Exception {
		log.debug("Request to update Customer: {}", customerDTO);

		Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
		customer = customerService.update(customer);

		return ResponseEntity.ok().body(customerMapper.customerToCustomerDTO(customer));
	}

	@DeleteMapping(value = "/{custId}")
	public ResponseEntity<?> delete(@PathVariable("custId") Integer custId) throws Exception {
		log.debug("Request to delete Customer");

		customerService.deleteById(custId);

		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/count")
	public ResponseEntity<Long> count() {
		return ResponseEntity.ok().body(customerService.count());
	}
}
