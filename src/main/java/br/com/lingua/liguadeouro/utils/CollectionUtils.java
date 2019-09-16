package br.com.lingua.liguadeouro.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Funções úteis para manipulação de coleções
 */
public class CollectionUtils {

    /**
     * Converte um Iterable em um List
     *
     * @param iterableEntities Iterable
     * @param <T>              Tipo Genérico
     * @return List
     */
    public static <T> List<T> getList(Iterable<T> iterableEntities) {
        return StreamSupport.stream(iterableEntities.spliterator(), false)
                .collect(Collectors.toList());
    }
}
