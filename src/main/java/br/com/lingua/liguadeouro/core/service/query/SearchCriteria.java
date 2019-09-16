package br.com.lingua.liguadeouro.core.service.query;

import lombok.Data;

@Data
class SearchCriteria {

    private String key;
    private SearchOperation operation;
    private Object value;
    private boolean orPredicate;

    SearchCriteria(final String key, final SearchOperation operation, final Object value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    SearchCriteria(final String orPredicate, final String key, final SearchOperation operation, final Object value) {
        super();
        this.orPredicate = orPredicate != null && orPredicate.equals(SearchOperation.OR_PREDICATE_FLAG);
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

}