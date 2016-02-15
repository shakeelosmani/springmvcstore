package com.kaushik.simplestore.service;

import com.kaushik.simplestore.domain.CustomerOrder;
import com.kaushik.simplestore.domain.OrderedProduct;
import com.kaushik.simplestore.domain.OrderedProductId;
import com.kaushik.simplestore.domain.Product;
import com.kaushik.simplestore.repository.OrderedProductDao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class OrderedProductService {

	@Resource
	private OrderedProductDao orderedProductDao;
	
	public void save(CustomerOrder order, Map<Product, Integer> items) {
		Set<Product> keys = items.keySet();
		Set<OrderedProduct> set = new HashSet<OrderedProduct>(0);
		for (Product key : keys) {
			int productId = key.getId();
			int orderId = order.getId();
			OrderedProductId orderedProductId = new OrderedProductId(orderId, productId);
			OrderedProduct orderedProduct = new OrderedProduct(orderedProductId, key, order, items.get(key).shortValue());
			set.add(orderedProduct);
		}
		orderedProductDao.save(set);
	}
	
}
