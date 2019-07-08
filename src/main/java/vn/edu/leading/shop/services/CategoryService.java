package vn.edu.leading.shop.services;

import vn.edu.leading.shop.models.CategoryModel;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryModel> findAll();

    List<CategoryModel> search(String term);

    Optional<CategoryModel> findById(Long id);

    Boolean update(CategoryModel category);

    CategoryModel save(CategoryModel category);

    Boolean delete(Long id);
}
