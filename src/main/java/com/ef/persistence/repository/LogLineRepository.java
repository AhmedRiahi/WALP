package com.ef.persistence.repository;

import com.ef.persistence.entity.LogLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogLineRepository extends JpaRepository<LogLineEntity, Long> {
}