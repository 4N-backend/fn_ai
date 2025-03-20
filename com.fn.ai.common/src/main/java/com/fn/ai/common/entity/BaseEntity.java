package com.fn.ai.common.entity;

import com.fn.ai.common.context.UserContextHolder;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    protected LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    protected String createdBy;

    @LastModifiedDate
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by")
    protected String updatedBy;

    @Column(name ="deleted_at")
    protected LocalDateTime deletedAt;

    @Column(name ="deleted_by")
    protected String deletedBy;

    /**
     * soft delete
     */
    public void delete(){
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = UserContextHolder.getUsername();
    }

    public void restore(){
        this.deletedAt = null;
        this.deletedBy = null;
    }

    public boolean isDeleted(){
        return deletedAt != null;
    }

}
