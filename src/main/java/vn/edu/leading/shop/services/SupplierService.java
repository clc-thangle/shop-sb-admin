package vn.edu.leading.shop.services;

import vn.edu.leading.shop.models.SupplierModel;

import java.util.List;
import java.util.Optional;

public interface SupplierService {

    List<SupplierModel> findAll();

    List<SupplierModel> search(String term);

    Optional<SupplierModel> findById(Long id);

    Boolean update(SupplierModel supplier);

    SupplierModel save(SupplierModel supplier);

    Boolean delete(Long id);
}
