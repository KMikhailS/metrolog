package ru.kmikhails.metrolog.service;

import ru.kmikhails.metrolog.repository.AbstractCrudRepository;

import java.util.List;

public abstract class AbstractService<E> {

    protected final AbstractCrudRepository<E> crudRepository;

    public AbstractService(AbstractCrudRepository<E> crudRepository) {
        this.crudRepository = crudRepository;
    }

    public void save(E entity) {
        crudRepository.save(entity);
    }

    public void deleteById(Long id) {
        crudRepository.delete(id);
    }

    public List<E> findAll() {
        return crudRepository.findAll();
    }

    public E findByName(String value) {
        return crudRepository.findByName(value);
    }
}
