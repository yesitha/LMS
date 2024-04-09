package com.itgura.validate;

import com.itgura.exception.ValueNotExistException;
import org.springframework.stereotype.Component;

@Component
public class RequestPropertyValidate<T> {
    public T validate(T t,String field) throws ValueNotExistException {
        if (t == null || t.equals("")) {
            throw new ValueNotExistException(field + " does not exist any value or it is an empty ");
        }

        return t;
    }
}
