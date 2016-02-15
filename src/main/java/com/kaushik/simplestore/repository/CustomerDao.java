package com.kaushik.simplestore.repository;

import org.springframework.data.repository.Repository;

import com.kaushik.simplestore.domain.Customer;

import java.util.List;

public interface CustomerDao extends Repository<Customer, Integer> {
	
	List<Customer> findAll();

	Customer findOne(Integer id);

	Customer findByEmail(String email);
	
	Customer save(Customer customer);

	Customer findByEmailAndPassword(String email, String password);

}
