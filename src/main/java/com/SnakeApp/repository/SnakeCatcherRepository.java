package com.SnakeApp.repository;

import com.SnakeApp.entity.SnakeCatcher;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SnakeCatcherRepository extends JpaRepository<SnakeCatcher, Long> {

    @Query(value = "SELECT COALESCE(MAX(snake_catcher_id), 0)   + 1 FROM snake_catcher", nativeQuery = true)
    int getNextSnakeCatcherId();

    SnakeCatcher findByEmailAndStatus(String email, int status);

    List<SnakeCatcher> findByStatus(int status);

    SnakeCatcher findBySnakeCatcherIdAndStatus(long snakeCatcherId, int status);

    SnakeCatcher findByRegNoAndStatus (String regNo, int status);
    List<SnakeCatcher> findByCityAndStatus(String city, int status);
}

