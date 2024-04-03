package com.SnakeApp.repository;

import com.SnakeApp.entity.SnakeCatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SnakeCatcherRepository extends JpaRepository<SnakeCatcher, Long> {

    @Query(value ="SELECT COALESCE(MAX(id), 0) + 1 FROM snake_catcher",nativeQuery = true)
    int getNextSnakeCatcherId();
}
