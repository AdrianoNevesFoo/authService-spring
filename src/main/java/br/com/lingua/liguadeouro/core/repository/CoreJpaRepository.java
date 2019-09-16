package br.com.lingua.liguadeouro.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

@NoRepositoryBean
public interface CoreJpaRepository<T, I> extends JpaRepository<T, I>, JpaSpecificationExecutor<T>, QueryByExampleExecutor<T> {

    List<T> findAllByDeletedFalse();

    Page<T> findAllByDeletedFalse(Pageable pageable);

    List<T> findAllByDeletedTrue();

    Page<T> findAllByDeletedTrue(Pageable pageable);

    Long countByDeletedFalse();

    Long countByDeletedTrue();

    List<T> findAllByDeletedFalseAndEnabledTrue();

    Page<T> findAllByDeletedFalseAndEnabledTrue(Pageable pageable);

    List<T> findAllByDeletedFalseAndEnabledFalse();

    Page<T> findAllByDeletedFalseAndEnabledFalse(Pageable pageable);

    Long countByDeletedFalseAndEnabledTrue();

    Long countByDeletedFalseAndEnabledFalse();
}
