package com.example.demo;

import java.util.*;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long>{
	List<Product> findAll();
	Optional<Product> findById(long id);
}
