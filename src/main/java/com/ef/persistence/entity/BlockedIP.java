package com.ef.persistence.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@Entity(name = "blocked_ip")
public class BlockedIP implements Persistable<Long> {

    @Id
    private Long id;
    private String ip;
    private String comment;

    @Override
    public boolean isNew() {
        return true;
    }
}
