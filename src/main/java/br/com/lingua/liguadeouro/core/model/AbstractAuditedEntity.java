package br.com.lingua.liguadeouro.core.model;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@MappedSuperclass
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditedEntity implements Entity<Long> {

    private static final long serialVersionUID = 1L;

    public abstract Long getId();

    @Getter
    @Setter
    @Column(name = "enabled", nullable = false)
    private boolean enabled = Boolean.TRUE;

    @Getter
    @Setter
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Getter
    @Setter
    @Column(name = "deleted_by")
    private String deletedBy;

    @Getter
    @Setter
    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;

    @Getter
    @Setter
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @Getter
    @Setter
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Getter
    @Setter
    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Getter
    @Setter
    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Getter
    @Setter
    @Version
    private Long version;
}