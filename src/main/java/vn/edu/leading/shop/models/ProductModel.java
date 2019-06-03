package vn.edu.leading.shop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "shop_products")
public class ProductModel extends BaseModel<ProductModel> {

    @NotEmpty
    @Column(name = "product_name", nullable = false)
    private String productName;

    private String unit;

    private Double price;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    @BatchSize(size = 50)
    @JsonManagedReference
    private SupplierModel supplierModel;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @BatchSize(size = 50)
    @JsonManagedReference
    private CategoryModel categoryModel;

    @OneToMany(
            mappedBy = "productModel",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 50)
    @JsonBackReference
    private List<OrderDetailModel> orderDetails = new ArrayList<>();

    private String urlImage;

    private String description;

    private String stt;
}
