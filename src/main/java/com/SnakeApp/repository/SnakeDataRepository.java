package com.SnakeApp.repository;

import com.SnakeApp.entity.SnakeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnakeDataRepository extends JpaRepository<SnakeData,Long> {
}
