package br.com.lingua.liguadeouro.core.service.query;

import org.springframework.data.jpa.domain.Specification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static br.com.lingua.liguadeouro.core.service.query.SearchOperation.EQUALITY;

public class ResolveSpecification<T> {


    public Specification<T> of(String searchParameters) {
        return this.of(searchParameters, false);
    }


    public Specification<T> of(String searchParameters, Boolean withTrashed) {
        final BaseSpecificationsBuilder<T> builder = new BaseSpecificationsBuilder<>();
        final String operationSetExper = String.join("|", SearchOperation.SIMPLE_OPERATION_SET);



        final Pattern pattern = Pattern.compile("(\\p{Punct}?)(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(searchParameters + ",");

        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3),
                    matcher.group(5), matcher.group(4), matcher.group(6));
        }

        builder.with(new SearchCriteria("deleted", EQUALITY, false));

        if (!withTrashed) {
            builder.with(new SearchCriteria("enabled", EQUALITY, true));
        }

        return builder.build();
    }

}