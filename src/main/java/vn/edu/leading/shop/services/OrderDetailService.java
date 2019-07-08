package vn.edu.leading.shop.services;


import vn.edu.leading.shop.models.OrderDetailModel;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {

    List<OrderDetailModel> findAll();

    Optional<OrderDetailModel> findById(Long id);

    Boolean update(OrderDetailModel orderDetail);

    OrderDetailModel save(OrderDetailModel orderDetail);

    Boolean delete(Long id);
}
