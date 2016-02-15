package com.kaushik.simplestore.service;

import com.kaushik.simplestore.domain.Category;
import com.kaushik.simplestore.repository.CategoryDao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

@Service
@Transactional
public class CategoryService {

	@Resource
	private CategoryDao categoryDao;
	
	public List<Category> getAll() {
		return categoryDao.findAll();
	}
	
}
