package vn.edu.leading.shop.controllers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import vn.edu.leading.shop.models.BaseModel;
import vn.edu.leading.shop.services.BaseService;

import javax.persistence.EntityManager;

public class BaseController<T extends BaseModel<T>> {

    @Getter
    @Autowired
    public EntityManager entityManager;
    @Getter
    @Autowired
    public PlatformTransactionManager transactionManager;
    @Getter
    @Autowired
    public TransactionTemplate transactionTemplate;

    protected BaseService<T> baseService;

    public BaseController(BaseService<T> baseService) {
        this.baseService = baseService;
    }
}
