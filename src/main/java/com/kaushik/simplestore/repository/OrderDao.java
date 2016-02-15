package com.kaushik.simplestore.repository;

import org.springframework.data.repository.Repository;

import com.kaushik.simplestore.domain.CustomerOrder;

import java.util.List;

public interface OrderDao extends Repository<CustomerOrder, Integer> {
		
	List<CustomerOrder> findAll();
	
	CustomerOrder save(CustomerOrder order);
	
}
