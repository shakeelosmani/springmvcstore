package com.kaushik.simplestore.repository;

import org.springframework.data.repository.Repository;

import com.kaushik.simplestore.domain.Category;

import java.util.List;

public interface CategoryDao extends Repository<Category, Byte> {

	List<Category> findAll();
	
}
