package com.SnakeApp.repository;

import com.SnakeApp.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin , Long> {
    @Query(value ="SELECT COALESCE(MAX(admin_id), 0) + 1 FROM admin",nativeQuery = true)
    int getNextAdminId();

    Admin findFirstByEmailAndStatus(String email, int status);
    List<Admin> findAllByStatus(int status);
    Admin findByRegNoAndStatus(String regNo, int status);
    Admin findByAdminIdAndStatus(long adminId, int status);

}
