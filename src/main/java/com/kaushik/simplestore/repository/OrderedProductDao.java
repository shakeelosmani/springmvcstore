package com.kaushik.simplestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaushik.simplestore.domain.OrderedProduct;
import com.kaushik.simplestore.domain.OrderedProductId;

public interface OrderedProductDao extends JpaRepository<OrderedProduct, OrderedProductId> {
	
}
