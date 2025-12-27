package com.psd.inventoryserive.service;


import java.lang.module.ModuleDescriptor.Builder;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psd.inventoryserive.controller.InventoryController;
import com.psd.inventoryserive.dto.InventoryResponse;
import com.psd.inventoryserive.model.Inventory;
import com.psd.inventoryserive.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;



@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServices {


	 private final  InventoryRepository inventoryRepository;

   /*
    @Transactional(readOnly = true)
	public List<InventoryResponse> isInStock(String skuCode) {
	
		//Inventory inventory= inventoryRepository.findBySkuCode(skuCode);
		log.info("isInStock stock called");
		return inventoryRepository.findBySkuCode(skuCode).stream().map(inventory->
			InventoryResponse.builder()
            .skuCode(inventory.getSkuCode())
            .isInStock(inventory.getQuantity() > 0)
            .build()
		).collect(Collectors.toList());
		
	}*/
	 @Transactional(readOnly = true)
	    public List<InventoryResponse> isInStock(List<String> skuCode) {
	        log.info("Checking Inventory");
	        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
	                .map(inventory ->
	                        InventoryResponse.builder()
	                                .skuCode(inventory.getSkuCode())
	                                .isInStock(inventory.getQuantity() > 0)
	                                .build()
	                ).toList();
	    }

	
}
