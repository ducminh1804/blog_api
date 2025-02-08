package com.ducminh.blogapi.entity.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@MappedSuperclass
@Data
@JsonIgnoreProperties(
        value = {"createBy", "updateBy"},
        allowGetters = true
)
public class UserAudit extends DateAudit {
    @CreatedBy
    private int createBy;

    @LastModifiedBy
    private int updateBy;
}
