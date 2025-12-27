package com.psd.product_service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.psd.productservice.ProductServiceApplication;
import com.psd.productservice.dto.ProductRequest;
import com.psd.productservice.repository.ProductRepository;

import ch.qos.logback.classic.Logger;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest(classes = ProductServiceApplication.class)
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer =
            new MongoDBContainer("mongo:5.0.2");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add(
            "spring.data.mongodb.uri",
            mongoDBContainer::getReplicaSetUrl
        );
    }

    @Autowired
    ProductRepository productRepository;
    
    @Test
    void shouldCreateProduct() throws Exception {

        ProductRequest productRequest = getProductRequest();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest))
        ).andExpect(status().isCreated());
        Assertions.assertEquals(1,     productRepository.findAll().size());
    }

    private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("iphone 13")
                .description("iphone13")
                .price(BigDecimal.valueOf(12345))
                .build();
    
    }
}
