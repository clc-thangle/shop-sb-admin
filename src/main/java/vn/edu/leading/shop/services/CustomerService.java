package vn.edu.leading.shop.services;

import vn.edu.leading.shop.models.CustomerModel;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<CustomerModel> findAll();

    List<CustomerModel> search(String term);

    Optional<CustomerModel> findById(Long id);

    Boolean update(CustomerModel customer);

    CustomerModel save(CustomerModel customer);

    Boolean delete(Long id);
}
