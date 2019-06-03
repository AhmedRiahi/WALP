package com.ef.persistence.repository;

import com.ef.persistence.entity.BlockedIP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedIPRepository extends JpaRepository<BlockedIP, Long> {
}