package com.spring.demo.msmail.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

import com.spring.demo.msmail.dto.ProductDto;
import com.spring.demo.msmail.dto.UserDto;
import java.util.concurrent.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Receiver {

    private final EmailService  emailService;
    private final ProductService productService;

    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "${spring.kafka.topic.user-created}")
    public void receiveUser(UserDto payload) {
        emailService.sendSimpleMessage(payload);
        latch.countDown();
    }

    @KafkaListener(topics = "${spring.kafka.topic.product-created}")
    public void receiveProduct(ProductDto payload) { // add payload from listener
        productService.createProduct(payload);
        latch.countDown();
    }
}
