package br.com.lingua.liguadeouro.auth.model;

import br.com.lingua.liguadeouro.core.model.AbstractAuditedEntity;
import br.com.lingua.liguadeouro.enums.RoleNameEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role extends AbstractAuditedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    public Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name="name", columnDefinition = "enum('ROLE_USER', 'ROLE_ADMIN')",   length = 60)
    public RoleNameEnum name;

}