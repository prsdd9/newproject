package com.psd.inventoryserive.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.psd.inventoryserive.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}





//@Query(value = "SELECT * FROM t_inventory WHERE sku_code = ?1", nativeQuery = true)
//List<Inventory> findBySkuCode(String skuCode);