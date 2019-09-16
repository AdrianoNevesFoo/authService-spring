package br.com.lingua.liguadeouro.core.service;

import br.com.lingua.liguadeouro.core.exceptions.UpdateConflictException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Interface básica para persistência de entidades
 *
 * @param <T> Entidade
 * @param <I> Tipo do Identificador da entidade, normalmente String para Mongo e Long para JPA
 */
public interface CoreService<T, I extends Serializable> {

    /**
     * Retorna todas as entidades ativas e não deletadas
     *
     * @return lista de entidades recuperada
     */
    List<T> list();

    /**
     * Retorna todas as entidades ativas e inativas e não deletadas
     *
     * @return lista de entidades recuperada
     */
    List<T> listWithTrashed();

    /**
     * Retorna todas as entidades inativas e não deletadas
     *
     * @return todas as entidades recuperada
     */
    List<T> listOnlyTrashed();

    /**
     * Retorna uma lista paginada de entidades ativas e não deletadas
     *
     * @param pageable
     * @return Página de entidades recuperadas
     */
    Page<T> list(Pageable pageable);

    /**
     * Retorna uma lista paginada de entidades ativas e inativas e não deletadas
     *
     * @param pageable
     * @return Página de entidades recuperadas
     */
    Page<T> listWithTrashed(Pageable pageable);

    /**
     * Retorna uma lista paginada de entidades inativas e não deletadas
     *
     * @param pageable
     * @return Página de entidades recuperadas
     */
    Page<T> listOnlyTrashed(Pageable pageable);

    List<T> list(Example<T> example);

    Page<T> list(Example<T> example, Pageable pageable);

    /**
     * Recupera todas as entidades que possuem Id presente na lista passada como parâmetro
     *
     * @param ids
     * @return lista de entidades recuperadas
     */
    List<T> list(Collection<I> ids);


    /**
     * Encontra um elemento a partir do seu ID, independente se a entidade está ativa ou deletada.
     *
     * @param id
     * @return Um optional da entidade encontrada
     */
    Optional<T> get(I id);

    /**
     * Salva uma entidade passada como parâmetro
     *
     * @param entity
     * @return Optional da entidade salva
     */
    Optional<T> save(final T entity);

    /**
     * Salva uma lista de entidades passadas como parâmetro
     *
     * @param entities
     * @return Lista das entidades salvas
     */
    List<T> save(Collection<T> entities);

    /**
     * Atualiza uma entidade passada como parâmetro. Para isso é necessário passar o Id da entidade e a própria
     * entidade como parâmetro. O método vai verificar se o Id realmente pertence à entidade, logo em seguida
     * verifica se a entidade existe na base de dados e por fim, aplica a operação de update.
     *
     * @param id     Id da entidade que se deseja atualizar
     * @param entity Entidade que se deseja atualizar
     * @return Entidade atualizada
     * @throws UpdateConflictException
     */
    T update(I id, T entity) throws UpdateConflictException;

    /**
     * Soft Delete: atualiza o campo "deleted_at" da entidade passada como parâmetro
     *
     * @param entity
     * @return Optional da entidade deletada
     */
    Optional<T> delete(T entity);

    /**
     * Deleta a entidade da base de dados, apagando de fato seu registro.
     *
     * @param entity
     * @return true se a entidade foi apagada e false caso contrário
     */
    boolean hardDelete(T entity);

    /**
     * Soft Delete: atualiza o campo "deleted_at" da entidade referente ao Id passado como parâmtro.
     *
     * @param id
     * @return
     */
    Optional<T> delete(I id);

    /**
     * Contagem de todas as entidades ativas e não deletadas
     *
     * @return número de entidades ativas e não deletadas
     */
    Long count();

    /**
     * Contagem de todas as entidades ativas e inativas e não deletadas
     *
     * @return número de entidades ativas, inativas e não deletadas
     */
    Long countWithTrashed();

    /**
     * Contagem de entidades inativas e não deletadas
     *
     * @return número de entidades ativas e não deletadas
     */
    Long countOnlyTrashed();

    /**
     * Verifica se a entidade referente ao Id passado como parâmetro existe na base de dados
     *
     * @param id
     * @return true se a entidade existir e false caso contrário
     */
    Boolean exists(I id);

    /**
     * Verifica se a entidade referente ao Exemplo passado como parâmetro existe na base de dados
     *
     * @param example
     * @return true se a entidade existir e false caso contrário
     */
    Boolean exists(Example<T> example);

    /**
     * Verifica se a entidade referente ao Exemplo passado como parâmetro existe na base de dados
     *
     * @param propertyName  Nome da propriedade do objeto ao qual se deseja procurar
     * @param propertyValue Valor da propriedade do objeto ao qual se deseja procurar
     * @return true se a entidade existir e false caso contrário
     */
    Boolean existsBy(String propertyName, String propertyValue);

    /**
     * Inativa a entidade passada como parâmetro, setando seu atributo "enabled" para false
     *
     * @param entity
     * @return entidade inativada
     */
    Optional<T> inactivate(T entity);

    /**
     * Inativa a entidade referente ao Id passado como parâmetro, setando seu atributo "enabled" para false
     *
     * @param id
     * @return entidade inativada
     */
    Optional<T> inactivate(I id);
}
