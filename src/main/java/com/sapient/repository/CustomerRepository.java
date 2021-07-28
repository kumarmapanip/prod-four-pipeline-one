package com.sapient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapient.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	public abstract Customer findByName(String name);
}
