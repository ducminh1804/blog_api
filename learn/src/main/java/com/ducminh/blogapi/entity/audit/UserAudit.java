package com.ducminh.blogapi.entity.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Data
@JsonIgnoreProperties(
        value = {"createBy", "updateBy"},
        allowGetters = true
)
public class UserAudit extends DateAudit {
    @CreatedBy
    private String createBy;

    @LastModifiedBy
    private String updateBy;
}
