package vn.edu.leading.shop.services;

import vn.edu.leading.shop.models.EmployeeModel;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<EmployeeModel> findAll();

    List<EmployeeModel> search(String term);

    Optional<EmployeeModel> findById(Long id);

    Boolean update(EmployeeModel employee);

    EmployeeModel save(EmployeeModel employee);

    Boolean delete(Long id);
}
