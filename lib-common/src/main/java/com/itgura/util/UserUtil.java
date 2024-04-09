package com.itgura.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Getter
@Setter
public class UserUtil {
    @Autowired
    private UserStore requestUserStore;

    public UUID getUserId(String jwtToken) throws JsonProcessingException {
        return getUserId();
    }
//    public static ThreadLocal<ThreadUserStore> threadUserStore = new ThreadLocal<>();

    public UUID getUserId() throws JsonProcessingException {
        return requestUserStore.getUserId();

//        if (null != RequestContextHolder.getRequestAttributes()) {
//            return requestUserStore.getUserId();
//        }
//        else {
//            try {
//                return threadUserStore.get().getUserId();
//            } catch (Exception e) {
//                return 1l;
//            }
//        }
    }
}
