package com.parkmate.reviewreadservice.common.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import java.time.Instant;

@Getter
public abstract class BaseEntity implements Persistable<String> {

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Override
    public boolean isNew() {
        return createdAt == null;
    }
}