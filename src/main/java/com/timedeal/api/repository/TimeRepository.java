package com.timedeal.api.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.timedeal.api.entity.Time;

public interface TimeRepository extends JpaRepository<Time, Long> {

}