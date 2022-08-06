package com.load.filter.load_filter_platform.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate updatedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String updatedBy;

    @PrePersist
    public void prePersist() {
        if (nonNull(this.createdDate) && nonNull(lastModifiedDate)) {
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        setCreatedDate(now);
        setLastModifiedDate(now);
        setCreatedBy();
    }

    @PreUpdate
    public void preUpdate() {
        setLastModifiedDate(LocalDateTime.now());
        setLastModifiedBy();
    }

    public void setCreatedBy() {
        if (nonNull(getAuthentication())) {
            UserDetailsImpl userDetails = (UserDetailsImpl) getAuthentication().getPrincipal();
            setCreatedBy(userDetails.getId());
            setLastModifiedBy(userDetails.getId());
        }
    }

    public void setLastModifiedBy() {
        if (nonNull(getAuthentication())) {
            UserDetailsImpl userDetails = (UserDetailsImpl) getAuthentication().getPrincipal();
            setLastModifiedBy(userDetails.getId());
        }
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
