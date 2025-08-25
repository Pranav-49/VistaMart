package com.pranav.eCommerce.service;

import com.pranav.eCommerce.dao.OrderDTO;
import com.pranav.eCommerce.dao.OrderItemDTO;
import com.pranav.eCommerce.model.*;
import com.pranav.eCommerce.repo.OrderRepository;
import com.pranav.eCommerce.repo.ProductRepository;
import com.pranav.eCommerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public OrderDTO placeOrderService(Long userId, Map<Long,Integer> productQuantity, double totalAmount)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User Not Found Exeption"));

        Orders orders = new Orders();
        orders.setId(userId);
        orders.setOrderDate(new Date());
        orders.setTotalAmount(totalAmount);
        orders.setStatus("Pendding");

        List<OrderItem> orderItems = new ArrayList<>();
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();

        for (Map.Entry<Long,Integer> entry : productQuantity.entrySet())
        {
            Product product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Product Not Found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(orders);
            orderItem.setProduct(product);
            orderItem.setQuantity(entry.getValue());
            orderItems.add(orderItem);

            orderItemDTOS.add(new OrderItemDTO(product.getName(), product.getPrice(),entry.getValue()));
        }

        orders.setOrderItems(orderItems);

        Orders saveOrder = orderRepository.save(orders);

        return new OrderDTO(saveOrder.getId(), saveOrder.getTotalAmount(), saveOrder.getStatus(),
                saveOrder.getOrderDate(),orderItemDTOS);
    }

    public List<OrderDTO> getAllOrders()
    {
        List<Orders> orders = orderRepository.findALlOrdersWithUsers();
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private OrderDTO convertToDTO(Orders orders) {
        List<OrderItemDTO> OrderItems = orders.getOrderItems().stream()
                .map(item -> new OrderItemDTO(
                item.getProduct().getName(),
                item.getProduct().getPrice(),
                item.getQuantity())).collect(Collectors.toList());

        return new OrderDTO(
                orders.getId(),
                orders.getTotalAmount(),
                orders.getStatus(),
                orders.getOrderDate(),
//                orders.getUser()!= null ? orders.getUser().getName():"Unknow",
//                orders.getUser()!= null ? orders.getUser().getEmail():"Unknow",
                OrderItems
        );
    }

    public List<OrderDTO> getOrderByUser(Long userId) {

        Optional<User> userOp = userRepository.findById(userId);

        if (userOp.isEmpty())
            throw new RuntimeException("User Not Found");
        User user = userOp.get();

        List<Orders> ordersList = orderRepository.findByUser(user);

        return ordersList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
