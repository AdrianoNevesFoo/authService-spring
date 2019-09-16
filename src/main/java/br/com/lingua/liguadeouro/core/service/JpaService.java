package br.com.lingua.liguadeouro.core.service;

import br.com.lingua.liguadeouro.auth.model.Role;
import br.com.lingua.liguadeouro.auth.model.User;
import br.com.lingua.liguadeouro.core.model.AbstractAuditedEntity;
import br.com.lingua.liguadeouro.core.exceptions.UpdateConflictException;
import br.com.lingua.liguadeouro.core.repository.CoreJpaRepository;
import br.com.lingua.liguadeouro.core.service.query.ResolveSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public abstract class JpaService<T extends AbstractAuditedEntity, I extends Serializable, R extends CoreJpaRepository<T, I>>
        extends CoreServiceImpl<T, I, R>
        implements CoreJpaService<T, I> {

    private final Class<T> entityType;

    @Autowired
    protected R repository;

    @Autowired
    private EntityManager em;

    protected JpaService(R repository, Class<T> entityType) {
        super(repository);
        this.repository = repository;
        this.entityType = entityType;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> list() {
        return this.repository.findAllByDeletedFalseAndEnabledTrue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> listWithTrashed() {
        return this.repository.findAllByDeletedFalse();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> listOnlyTrashed() {
        return this.repository.findAllByDeletedFalseAndEnabledFalse();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<T> list(final Pageable pageable) {
        return this.repository.findAllByDeletedFalseAndEnabledTrue(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<T> listWithTrashed(final Pageable pageable) {
        return this.repository.findAllByDeletedFalse(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<T> listOnlyTrashed(final Pageable pageable) {
        return this.repository.findAllByDeletedFalseAndEnabledFalse(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> search(final Specification<T> specification) {
        return this.repository.findAll(specification);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> search(final String searchParameters) {
        final ResolveSpecification<T> resolveSpecification = new ResolveSpecification<>();
        final Specification<T> specification = resolveSpecification.of(searchParameters);

        return this.search(specification);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<T> search(final String searchParameters, Pageable pageable) {
        final ResolveSpecification<T> resolveSpecification = new ResolveSpecification<>();
        final Specification<T> specification = resolveSpecification.of(searchParameters);

        return this.search(specification, pageable);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Page<T> search(final Specification<T> specification, Pageable pageable) {
        return this.repository.findAll(specification, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<T> findOne(String searchParameters) {
        final ResolveSpecification<T> resolveSpecification = new ResolveSpecification<>();
        final Specification<T> specification = resolveSpecification.of(searchParameters);

        return this.repository.findOne(specification);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<T> findOne(Example<T> example) {
        return this.repository.findOne(example);
    }

    @Override
    public List<T> list(Example<T> example) {
        return this.repository.findAll(example);
    }

    @Override
    public Page<T> list(Example<T> example, Pageable pageable) {
        return this.repository.findAll(example, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> list(Collection<I> ids) {
        return this.repository.findAllById(ids);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public T update(I id, T entity) throws UpdateConflictException {

        if (!id.equals(entity.getId())) {
            throw new UpdateConflictException(entityType.getName());
        }

        return this.get(id)
                .flatMap(existing -> this.save(entity))
                .orElseThrow(() -> new UpdateConflictException(entityType.getName()));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long count() {
        return this.repository.countByDeletedFalseAndEnabledTrue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long countWithTrashed() {
        return this.repository.countByDeletedFalse();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long countOnlyTrashed() {
        return this.repository.countByDeletedFalseAndEnabledFalse();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean exists(I id) {
        return this.repository.existsById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean exists(Example<T> example) {
        return this.repository.exists(example);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean existsBy(String propertyName, String propertyValue) {
        List<T> entitiesFound = this.search(String.format("%s:%s,deletedAt^null", propertyName, propertyValue));
        return !entitiesFound.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<T> inactivate(T entity) {
        entity.setEnabled(Boolean.FALSE);
        return this.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<T> inactivate(I id) {
        return this.get(id).flatMap(this::inactivate);
    }
}
