package com.ef.persistence.entity;

import lombok.Data;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity(name = "LogLine")
public class LogLineEntity implements Persistable<Long> {

    @Id
    private Long id;
    private LocalDateTime dateTime;
    private String ip;

    @Override
    public boolean isNew() {
        return true;
    }
}
