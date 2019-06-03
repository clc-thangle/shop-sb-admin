package vn.edu.leading.shop.repositories;

import org.springframework.stereotype.Repository;
import vn.edu.leading.shop.models.ConfigModel;
import vn.edu.leading.shop.models.OrderModel;

@Repository
public interface ConfigRepository extends BaseRepository<ConfigModel, String> {

}
