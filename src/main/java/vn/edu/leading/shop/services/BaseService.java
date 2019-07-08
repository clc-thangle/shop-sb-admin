package vn.edu.leading.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.leading.shop.models.BaseModel;
import vn.edu.leading.shop.repositories.BaseRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class BaseService<T extends BaseModel<T>> implements Serializable {

    private static final long serialVersionUID = 1L;

    protected final Class<T> entityClass;

    protected BaseRepository<T, Long> baseRepository;

    public BaseService() {
        ResolvableType resolvableType = ResolvableType.forClass(getClass());
        entityClass = (Class<T>) resolvableType.as(BaseService.class).getGeneric(0).resolve();
    }

    @Autowired
    public void setBaseRepository(BaseRepository<T, Long> baseRepository) {
        this.baseRepository = baseRepository;
    }

    public T newEntity() {
        try {
            return entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    @Transactional
    public T save(T entity) {
        return baseRepository.save(entity);
    }

    @Transactional
    public <S extends T> S saveAndFlush(S entity) {
        return baseRepository.saveAndFlush(entity);
    }

    @Transactional
    public Boolean delete(Long id) {
        T entity = findById(id).orElse(null);
        if (entity == null)
            return false;
        baseRepository.delete(entity);
        return true;
    }

    @Transactional
    public void deleteInBatch(Iterable<T> entities) {
        baseRepository.deleteInBatch(entities);
    }

    @Transactional
    public void deleteAllInBatch() {
        baseRepository.deleteAllInBatch();
    }

    public T getOne(T entity) {
        return baseRepository.getOne(entity.getId());
    }

    public List<T> findAll() {
        return baseRepository.findAll();
    }

    public List<T> findAll(Sort sort) {
        return baseRepository.findAll(sort);
    }

    public Page<T> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    public Optional<T> findById(Long id) {
        return baseRepository.findById(id);
    }

    public long count() {
        return baseRepository.count();
    }

    public Boolean update(T entity) {
        T t = baseRepository.findById(entity.getId()).orElse(null);
        if (t == null)
            return false;
        baseRepository.save(entity);
        return true;
    }

    public List<T> search(String attributeName, String text) {
        return baseRepository.findByAttributeContainsText(attributeName, text);
    }
}
