package br.com.lingua.liguadeouro.auth.model;


import br.com.lingua.liguadeouro.core.model.AbstractAuditedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Entity
//@Cacheable
@Table(name = "users")
public class User extends AbstractAuditedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "name", length = 150)
    private String name;

    @Column(name = "username", length = 150)
    private String username;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "password", length = 150)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public User(){

    }

    public User(Long id){
        this.id = id;
    }


    public User(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}