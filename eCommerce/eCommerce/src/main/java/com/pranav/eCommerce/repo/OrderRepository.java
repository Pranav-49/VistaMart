package com.pranav.eCommerce.repo;

import com.pranav.eCommerce.dao.OrderDTO;
import com.pranav.eCommerce.model.Orders;
import com.pranav.eCommerce.model.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
    @Query("SELECT o from Orders o JOIN FETCH o.user")
    List<Orders> findALlOrdersWithUsers();

    List<Orders> findByUser(User user);
}
