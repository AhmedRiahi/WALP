package com.ef.persistence.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@Entity(name = "blocked_ip")
public class BlockedIP {

    @Id
    @GeneratedValue
    private Long id;
    private String ip;
    private String comment;
}
