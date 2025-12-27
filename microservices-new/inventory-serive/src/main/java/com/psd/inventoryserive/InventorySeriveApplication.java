package com.psd.inventoryserive;

import org.aspectj.weaver.patterns.ArgsAnnotationPointcut;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.psd.inventoryserive.dto.InventoryResponse;
import com.psd.inventoryserive.model.Inventory;
import com.psd.inventoryserive.repository.InventoryRepository;

@SpringBootApplication
public class InventorySeriveApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventorySeriveApplication.class, args);
	}
	
	/*
	 * @Bean public CommandLineRunner createInventory(InventoryRepository
	 * repository) {
	 * 
	 * return args -> { Inventory inventory = new Inventory();
	 * inventory.setSkuCode("iphone 16"); inventory.setQuantity(30);
	 * 
	 * Inventory inventory1 = new Inventory(); inventory1.setSkuCode("iphone 17");
	 * inventory1.setQuantity(130);
	 * 
	 * repository.save(inventory); repository.save(inventory1);
	 * 
	 * 
	 * };
	 * 
	 * }
	 */

}
