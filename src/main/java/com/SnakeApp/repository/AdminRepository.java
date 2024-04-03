package com.SnakeApp.repository;

import com.SnakeApp.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin , Long> {
    @Query(value ="SELECT COALESCE(MAX(admin_id), 0) + 1 FROM admin",nativeQuery = true)
    int getNextAdminId();

    //Admin findByEmail(String email);
    @Query(value = "SELECT * FROM admin WHERE email = ?1 LIMIT 1", nativeQuery = true)
    Admin findByEmail(String email);
}
