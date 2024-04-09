package com.itgura.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
@SuppressWarnings({"rawtypes", "unchecked"})
public class PaginationUtil {

    @PersistenceContext
    private EntityManager entityManager;
    private static final String REGEX = "(SELECT)(.*page_param)(.*)";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    public PageImpl getPageResult(Query dataQuery, int page, int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);

        Long count;
        if (dataQuery.unwrap(org.hibernate.query.NativeQuery.class).getQueryString().matches("(?i).*GROUP BY.*")) {
            count =dataQuery.getResultList().stream().count();
        } else {
            Query countQuery = getCountQuery(dataQuery);
            dataQuery.getParameters().forEach(param -> {
                String paramName = param.getName();
                countQuery.setParameter(paramName, dataQuery.getParameterValue(paramName));
            });
            count = (Long) countQuery.getSingleResult();
        }

        dataQuery.setFirstResult((int) pageable.getOffset());
        dataQuery.setMaxResults(pageable.getPageSize());
        List result = dataQuery.getResultList();

//        if(result.isEmpty()) {
//        	count = new BigInteger("0");
//        }
//        if(result.size() < 10) {
//        	count = new BigInteger("0");
//        }

//    	List<BigInteger> resultList = countQuery.getResultList();
//    	BigInteger count = resultList == null || resultList.isEmpty() ? null : resultList.get(0);

        return new PageImpl<>(result, pageable, count.longValue());
    }
    private Query getCountQuery(Query dataQuery) {
        String dataSql = dataQuery.unwrap(org.hibernate.query.NativeQuery.class).getQueryString();

        StringBuilder countQuery = new StringBuilder();
        countQuery.append("SELECT count(*) FROM ( ");
        countQuery.append(dataSql);
        countQuery.append(" ) count");
        return this.entityManager.createNativeQuery(countQuery.toString());
    }

}
