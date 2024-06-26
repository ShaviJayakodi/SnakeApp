package com.SnakeApp.repository;

import com.SnakeApp.entity.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users , Long> {

    @Query(value ="SELECT COALESCE(MAX(user_id), 0) + 1 FROM users",nativeQuery = true)
    int getNextUserId();

    //Users findByEnEmail(String eMail);
    //@Query(value = "SELECT * FROM users WHERE en_email =?1 LIMIT 1", nativeQuery = true)
    Users findByEnEmailAndStatus(String enEmail,int status);
    Users findByRegNo(String regNo);
    Users findByRegNoAndStatus(String regNo, int status);

    Users findByUserIdAndStatus(long userId, int status);
}
