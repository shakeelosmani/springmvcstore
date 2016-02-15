package com.kaushik.simplestore.repository;

import org.springframework.data.repository.Repository;

import com.kaushik.simplestore.domain.Product;

import java.util.List;

public interface ProductDao extends Repository<Product, Integer> {
	
	List<Product> findByCategoryId(Byte id);
	
	Product findOne(Integer id);
	
	List<Product> findAll();
	
}
