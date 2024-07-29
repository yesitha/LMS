package com.itgura.dao.impl;

import com.itgura.dao.UserDao;
import com.itgura.request.dto.UserResponseDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.UUID;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;
    public UserResponseDto getUserDetailByEmail(String email) throws CredentialNotFoundException {
        try{
            StringBuilder sql = new StringBuilder();
            sql.append("select u.id, u.role, concat(u.first_name,' ',u.last_name) as username from auth_service._user as u where u.email like :email");
            java.util.List<Object[]> resultList ;

            Query nativeQuery = entityManager.createNativeQuery(sql.toString());
            nativeQuery.setParameter("email",email).getResultList();
            resultList = nativeQuery.getResultList();
            UserResponseDto response = new UserResponseDto();
            response.setUserId((UUID) resultList.get(0)[0]);
            response.setUserRoles((String) resultList.get(0)[1]);
            response.setName((String) resultList.get(0)[2]);
            return response;

        } catch (Exception e) {
            throw new CredentialNotFoundException("User not found");
        }
    }
}
