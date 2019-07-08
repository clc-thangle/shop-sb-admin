package vn.edu.leading.shop.services;

import vn.edu.leading.shop.models.OrderDetailModel;
import vn.edu.leading.shop.models.OrderModel;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderModel> findAll();

    List<OrderDetailModel> findAllOrderDetailById(Long id);

    Optional<OrderModel> findById(Long id);

    Boolean update(OrderModel order);

    OrderModel save(OrderModel order);

    Boolean delete(Long id);
}
