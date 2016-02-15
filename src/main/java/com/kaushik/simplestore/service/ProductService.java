package com.kaushik.simplestore.service;

import com.kaushik.simplestore.domain.Product;
import com.kaushik.simplestore.repository.ProductDao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

@Service
@Transactional
public class ProductService {

	@Resource
	private ProductDao productDao;
	
	public List<Product> getAll(){
		return productDao.findAll();
	}
	
	public List<Product> getByCategoryId(Byte id) {
		return productDao.findByCategoryId(id);
	}
	
	public Product getById(Integer id) {
		return productDao.findOne(id);
	}
	
}
