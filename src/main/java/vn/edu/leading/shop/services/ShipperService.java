package vn.edu.leading.shop.services;

import vn.edu.leading.shop.models.ShipperModel;

import java.util.List;
import java.util.Optional;

public interface ShipperService {

    List<ShipperModel> findAll();

    List<ShipperModel> search(String term);

    Optional<ShipperModel> findById(Long id);

    Boolean update(ShipperModel shipper);

    ShipperModel save(ShipperModel shipper);

    Boolean delete(Long id);
}
