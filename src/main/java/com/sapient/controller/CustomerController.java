package com.sapient.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.sapient.entity.Customer;
import com.sapient.entity.PurchaseHistory;
import com.sapient.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/c1")
@Api
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@ApiOperation (value = "Find All Customers",			
			produces = "List of Customers",
			response = Customer.class,
			tags = "findAllCustomer",
			notes = "http://localhost:9003/cc/c1/getAll"
			)
	@GetMapping("/getAll")
	public ResponseEntity<List<Customer> > getCustomers(){
		System.out.println("Exceptpion");
		try {
			List<Customer> customerList = customerService.getAllCustomers();
			for(Customer customer: customerList) {
				PurchaseHistory purchaseHistory = restTemplate.getForObject("http://localhost:9005/ph/get/"+customer.getCustomerId(), PurchaseHistory.class);
				customer.setPurchaseHistory(purchaseHistory);
			}
			return new ResponseEntity<>(customerList,HttpStatus.OK);
		}catch(ResponseStatusException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
		}
	}
	
	@ApiOperation (value = "Find Customer By Id",			
			produces = "Customer Id",
			response = Customer.class,
			tags = "findCustomer",
			notes = "http://localhost:9003/cc/c1/getid/1"
			)
	// http://localhost:9003/cc/c1/getid/5012
	@GetMapping("/getid/{customerId}")
	public ResponseEntity<Customer>  getCustomers(@PathVariable("customerId") Integer customerId){
		try {
			Customer customer =  customerService.getDetailsById(customerId);
			PurchaseHistory purchaseHistory = restTemplate.getForObject("http://localhost:9005/ph/get/"+customer.getCustomerId(), PurchaseHistory.class);
			customer.setPurchaseHistory(purchaseHistory);
			return new ResponseEntity<>(customer,HttpStatus.OK);
		}catch(ResponseStatusException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
		}
	}
	
	@ApiOperation (value = "Push Customer",			
			produces = "Customer Object",
			response = Customer.class,
			tags = "insertCustomer",
			notes = "http://localhost:9003/cc/c1/pushinto"
			)
	
	@PostMapping("/pushinto")
	public ResponseEntity<Customer>  addCustomer(@Valid @RequestBody Customer customer,
			BindingResult bindingResult) throws Exception{
			
		if(bindingResult.hasErrors())
				throw new Exception(bindingResult.getAllErrors().toString());
		/*PurchaseHistory purchaseHistory = restTemplate.getForObject("http://localhost:9005/ph/get/"+customer.getCustomerId(), PurchaseHistory.class);
		customer.setPurchaseHistory(purchaseHistory);*/
		Customer customerFromDb = customerService.addElement(customer);
		return ResponseEntity.ok().body(customerFromDb);
	}
	
	@ApiOperation (value = "Update Customer",			
			produces = "Customer Object",
			response = Customer.class,
			tags = "updateCustomer",
			notes = "http://localhost:9003/cc/c1/update"
			)
	@PutMapping("/update")
	public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer, BindingResult bindingResult) throws Exception{
		if(bindingResult.hasErrors())
			throw new Exception(bindingResult.getAllErrors().toString());
		
		Customer customerUpdated = customerService.updateElement(customer);
		return ResponseEntity.ok(customerUpdated);
	}
	
	@ApiOperation (value = "Delete Customer",			
			produces = "Customer Object",
			response = Customer.class,
			tags = "deleteCustomer",
			notes = "http://localhost:9003/cc/c1/delete"
			)
	@DeleteMapping("/delete/{customerId}")
	public ResponseEntity<Integer> deleteCustomer(@PathVariable("customerId") Integer customerId){
		Integer deleted= customerService.deleteElemet(customerId);
		return ResponseEntity.ok(deleted);
	}
	
	
}
