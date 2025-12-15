package com.example.order_service.Controller;

import com.example.order_service.Dto.OrderResponseDto;
import com.example.order_service.Dto.ProductDto;
import com.example.order_service.Entity.Order;
import com.example.order_service.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @PostMapping("/placeOrder")
    public Mono<ResponseEntity<OrderResponseDto>> placeOrder(@RequestBody Order order){
        return webClientBuilder.build().get().uri("http://localhost:8081/products/"+ order .getProductId())
                .retrieve()
                .bodyToMono(ProductDto.class)
                .map(productDto -> {
                   // Order savedOrder = orderRepository.save(order);
                    OrderResponseDto responseDto = new OrderResponseDto();

                    responseDto.setProductId(order.getProductId());
                    responseDto.setQuantity(order.getQuantity());

                    //set product details
                    responseDto.setProductName(productDto.getName());
                    responseDto.setProductPrice(productDto.getPrice());
                    responseDto.setTotalPrice(productDto.getPrice() * order.getQuantity());

                    //save order details
                    orderRepository.save(order);
                    responseDto.setOrderId(order.getId());
                    return ResponseEntity.ok(responseDto);

                });
    }

    @GetMapping
    public List<Order> getAllOrder(){
        return orderRepository.findAll();

    }

}
