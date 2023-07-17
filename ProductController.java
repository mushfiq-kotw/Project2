package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
@RestController
@RequestMapping("prodStock")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private WarehouseRepository warehouseRepository;
	
	@GetMapping("AddProd")
	public String addProd(@RequestBody ProdNWare prodNWare)
	{	Product prod = new Product();
		Warehouse wh= new Warehouse();
		prod.setProdName(prodNWare.getProdName());
		prod.setProdDesc(prodNWare.getProdDesc());
		prod.setQtyAvailable(prodNWare.getQtyAvailable());
		wh.setName(prodNWare.getWareName());
		wh.setDescription(prodNWare.getWareDesc());
		warehouseRepository.save(wh);
		prod.setWarehouse(wh);
		productRepository.save(prod);
		
		return "Product has been added successfully";
		
	}
	@GetMapping("FetchAllProducts")
	public ResponseEntity<List<Product>> fetchAllProducts()
	{	return ResponseEntity.ok(productRepository.findAll());
	}
	@GetMapping("FetchById")
	public ResponseEntity<Product> fetchById(@RequestParam long id)
	{	Optional<Product> optprod=productRepository.findById(id);
		if(optprod.isPresent())
			return ResponseEntity.ok(optprod.get());
		else
			return ResponseEntity.notFound().build();
	}
	@GetMapping("update")
	public String update(@RequestBody ProductStockRequest prodStockRequest)
	{	Optional<Product> optfetchedprod = productRepository.findById(prodStockRequest.getProductId());
		if(optfetchedprod.isPresent())
		{	Product fetchedprod = optfetchedprod.get();  
			fetchedprod.setQtyAvailable(fetchedprod.getQtyAvailable()+prodStockRequest.getQty());
			productRepository.save(fetchedprod);
			return "Product stock updated successfully";
		}	
		else
			return "Product not found in the inventory."; 
	}
	
}
