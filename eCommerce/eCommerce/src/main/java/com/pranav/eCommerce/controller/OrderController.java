package com.pranav.eCommerce.controller;


import com.pranav.eCommerce.dao.OrderDTO;
import com.pranav.eCommerce.model.OrderRequest;
import com.pranav.eCommerce.model.User;
import com.pranav.eCommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place/{userId}")
    public OrderDTO placeOrder(@PathVariable Long userId, @RequestBody OrderRequest orderRequest){
        return orderService.placeOrderService(userId,orderRequest.getProductQuantity(),orderRequest.getTotalAmount());
    }

    @GetMapping("all-orders")
    public List<OrderDTO> getAllOrders()
    {
        return orderService.getAllOrders();
    }

    @GetMapping("/user/{userId}")
    public List<OrderDTO> getOrderByUser(@PathVariable Long userId)
    {
        return orderService.getOrderByUser(userId);
    }
}
