package com.psd.orderserive.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import com.psd.orderserive.dto.InventoryResponse;
import com.psd.orderserive.dto.OrderLineItemsDto;
import com.psd.orderserive.dto.OrderRequest;
import com.psd.orderserive.model.Order;
import com.psd.orderserive.model.OrderLineItems;
import com.psd.orderserive.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;
	private final WebClient webClient;
	
	public void placeOrder (OrderRequest orderRequest) {
		
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		log.info("calling place order sercie");
		//orderRequest.getOrderLineItemsDtoList().stream().map(OrderLineItemsDto->mapToDto(OrderLineItemsDto)).toList();
		List<OrderLineItems> orderLineItems= orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto).toList();
		order.setOrderLineItems(orderLineItems);
		//call inventory
		///?skuCode=iphone16&skuCode=iphone13-red
		//get sku codes
		List<String> skuCodes = orderLineItems.stream().map(OrderLineItems::getSkuCode).collect(Collectors.toList());
		log.info("calling inventory service:"+skuCodes.toString());
		
		/*
		 * String inventoryListStr= webClient.get()
		 * .uri("http://localhost:8082/api/inventory/",uriBuilder->uriBuilder.queryParam
		 * ("skuCode", skuCodes).build()) .retrieve() .bodyToMono(String.class)
		 * .block(); log.info("inventoryListStr:"+inventoryListStr);
		 */
		InventoryResponse[] inventoryList= webClient.get()
		.uri("http://localhost:8082/api/inventory/",uriBuilder->uriBuilder.queryParam("skuCode", skuCodes).build())
		.retrieve()
		.bodyToMono(InventoryResponse[].class)
		.block();
		log.info("list count sending:"+skuCodes.size());
		log.info("respons size:"+inventoryList.length);
		//boolean allProductoInStock = Arrays.stream(inventoryList).allMatch(InventoryResponse::isInStock);
		boolean allProductoInStock=false;
		
		//allProductoInStock = Arrays.stream(inventoryList).allMatch(InventoryResponse::isInStock);
		allProductoInStock = Arrays.stream(inventoryList).findAny().isPresent()&&Arrays.stream(inventoryList).allMatch(inventory ->inventory.isInStock());
			
		log.info("allproductoInStock:"+allProductoInStock);
		if(allProductoInStock) {
			orderRepository.save(order);
		}else {
			throw new IllegalArgumentException("Products not in stock, Please try again Later !!");
		}
	}
	
	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		
		return new OrderLineItems().builder()
				.skuCode(orderLineItemsDto.getSkuCode())
				.price(orderLineItemsDto.getPrice())
				.quantity(orderLineItemsDto.getQuantity())
				.build();
		
	}
}
