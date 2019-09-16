package br.com.lingua.liguadeouro.core.service;

import br.com.lingua.liguadeouro.core.model.Entity;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class CoreServiceImpl<T extends Entity, I extends Serializable, R extends CrudRepository<T, I>> implements CoreService<T, I> {

    private R repository;

    protected CoreServiceImpl(R repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<T> get(I id) {
        return this.repository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<T> save(T entity) {
        return Optional.of(this.repository.save(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> save(Collection<T> entities) {
        final Iterable<T> iterable = this.repository.saveAll(entities);
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<T> delete(T entity) {
        entity.setDeletedAt(LocalDateTime.now());
        entity.setDeleted(Boolean.TRUE);
        return this.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hardDelete(T entity) {
        try {
            this.repository.delete(entity);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<T> delete(I id) {
        return this.get(id).flatMap(existing -> {
            existing.setDeletedAt(LocalDateTime.now());
            existing.setDeleted(Boolean.TRUE);

            return this.save(existing);
        });
    }


}
