package com.psd.inventoryserive.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.psd.inventoryserive.dto.InventoryResponse;
import com.psd.inventoryserive.service.InventoryServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("/api/inventory/")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryServices inventoryService;
	
	/*@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryResponse> checkInventory(@RequestParam("skuCode") String skuCode) {
		log.info("sku code:{}",skuCode);
		return inventoryService.isInStock(skuCode);
		
	}*/
	
    // http://localhost:8082/api/inventory/iphone-13,iphone13-red

    // http://localhost:8082/api/inventory?skuCode=iphone-13&skuCode=iphone13-red
	
	  @GetMapping()
	  
	  @ResponseStatus(HttpStatus.OK) public List<InventoryResponse>
	  isInStock(@RequestParam List<String> skuCode) {
	  log.info("Received inventory check request for skuCode: {}", skuCode); 
	  return
	  inventoryService.isInStock(skuCode); }
	 
	/*
	 * @GetMapping("/{sku-code}")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public List<InventoryResponse>
	 * isInStock(@PathVariable("sku-code") List<String> skuCode) {
	 * log.info("Received inventory check request for skuCode: {}", skuCode); return
	 * inventoryService.isInStock(skuCode); }
	 */

}
