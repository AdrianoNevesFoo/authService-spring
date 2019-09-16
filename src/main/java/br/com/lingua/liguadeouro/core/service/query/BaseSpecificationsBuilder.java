package br.com.lingua.liguadeouro.core.service.query;

import org.springframework.data.jpa.domain.Specification;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BaseSpecificationsBuilder<T> {

    private final List<SearchCriteria> params;

    public BaseSpecificationsBuilder() {
        params = new ArrayList<>();
    }
    // API

    public final BaseSpecificationsBuilder<T> with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public final BaseSpecificationsBuilder<T> with(final String orPredicate, final String key, final String operation, final Object value, final String prefix, final String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new SearchCriteria(orPredicate, key, op, value));
        }
        return this;
    }

    public Specification<T> build(Function<SearchCriteria, Specification<T>> converter) {

        if (params.isEmpty()) {
            return null;
        }

        final List<Specification<T>> specs = params.stream()
                .map(converter)
                .collect(Collectors.toCollection(ArrayList::new));

        Specification<T> result = specs.get(0);

        for (int idx = 1; idx < specs.size(); idx++) {
            result = params.get(idx)
                    .isOrPredicate()
                    ? Specification.where(result)
                    .or(specs.get(idx))
                    : Specification.where(result)
                    .and(specs.get(idx));
        }

        return result;
    }

    public Specification<T> build(Deque<?> postFixedExprStack, Function<SearchCriteria, Specification<T>> converter) {

        Deque<Specification<T>> specStack = new LinkedList<>();

        Collections.reverse((List<?>) postFixedExprStack);

        while (!postFixedExprStack.isEmpty()) {
            Object mayBeOperand = postFixedExprStack.pop();

            if (!(mayBeOperand instanceof String)) {
                specStack.push(converter.apply((SearchCriteria) mayBeOperand));
            } else {
                Specification<T> operand1 = specStack.pop();
                Specification<T> operand2 = specStack.pop();
                if (mayBeOperand.equals(SearchOperation.AND_OPERATOR))
                    specStack.push(Specification.where(operand1)
                            .and(operand2));
                else if (mayBeOperand.equals(SearchOperation.OR_OPERATOR))
                    specStack.push(Specification.where(operand1)
                            .or(operand2));
            }

        }
        return specStack.pop();

    }

    public Specification<T> build() {
        if (params.isEmpty()) {
            return null;
        }

        Specification<T> result = new BaseSpecification<>(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new BaseSpecification<>(params.get(i)))
                    : Specification.where(result).and(new BaseSpecification<>(params.get(i)));
        }

        return result;
    }

    public final BaseSpecificationsBuilder<T> with(BaseSpecification<T> spec) {
        params.add(spec.getCriteria());
        return this;
    }

    public final BaseSpecificationsBuilder<T> with(SearchCriteria criteria) {
        params.add(criteria);
        return this;
    }
}

