package ru.kmikhails.metrolog.service;

import ru.kmikhails.metrolog.repository.AbstractCrudRepository;

import java.util.List;
import java.util.stream.Collectors;

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
        return crudRepository.findAll().stream().sorted().collect(Collectors.toList());
    }

    public E findByName(String value) {
        return crudRepository.findByName(value);
    }
}
