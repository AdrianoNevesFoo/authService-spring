package br.com.lingua.liguadeouro.core.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Interface básica para persistência de entidades
 *
 * @param <T> Entidade
 * @param <I> Tipo do Identificador da entidade, normalmente String para Mongo e Long para JPA
 */
public interface CoreJpaService<T, I extends Serializable> extends CoreService<T, I> {

    /**
     * Lista todas as entidades não deletadas
     *
     * @return a listas de todas as entidades do banco
     */
    List<T> search(Specification<T> specification);

    /**
     * Lista todas as entidades não deletadas
     *
     * @return a listas de todas as entidades do banco
     */
    Page<T> search(Specification<T> specification, Pageable pageable);

    /**
     * Lista todas as entidades não deletadas
     *
     * @return a listas de todas as entidades do banco
     */
    List<T> search(String searchParameters);

    /**
     * Lista todas as entidades não deletadas
     *
     * @return a listas de todas as entidades do banco
     */
    Page<T> search(String searchParameters, Pageable pageable);

    /**
     * Encontra uma unica entidade que satisfaz aos parâmetros e que não seja deletada
     *
     * @return entidade que satisfaz aos parâmetros
     */
    Optional<T> findOne(String searchParameters);

    /**
     * Encontra uma unica entidade que satisfaz aos parâmetros e que não seja deletada
     *
     * @return entidade que satisfaz aos parâmetros
     */
    Optional<T> findOne(Example<T> example);

    /**
     * Veriifica se exite uma entidade de acordo com os parâmetros passados
     *
     * @return true se existe uma entidade que satisfaz os parâmetros e false caso contrário
     */
    Boolean existsBy(String propertyName, String propertyValue);
}
