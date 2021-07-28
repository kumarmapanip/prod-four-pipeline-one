package com.sapient.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapient.entity.Customer;
import com.sapient.repository.CustomerRepository;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public Customer getDetailsById(Integer id) {
		return customerRepository.findById(id).get();
	}

	@Override
	public Customer addElement(Customer customer) {		
		return customerRepository.save(customer);
	}

	@Override
	public Customer updateElement(Customer customer) {		
		try{
			return customerRepository.save(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer deleteElemet(Integer customerId) {
		Customer customer = customerRepository.findById(customerId).get();
		customerRepository.delete(customer);
		return customerId;
	}
	
		
}
