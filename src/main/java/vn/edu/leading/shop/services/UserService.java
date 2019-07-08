package vn.edu.leading.shop.services;

import vn.edu.leading.shop.models.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserModel> findAll();

    List<UserModel> search(String term);

    Optional<UserModel> findById(Long id);

    Optional<UserModel> findByUsername(String username);

    Boolean update(UserModel userModel);

    UserModel save(UserModel userModel);

    void register(UserModel userModel) throws Exception;

    Boolean delete(Long id);
}
