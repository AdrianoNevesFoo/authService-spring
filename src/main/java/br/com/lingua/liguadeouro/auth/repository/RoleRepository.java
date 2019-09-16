package br.com.lingua.liguadeouro.auth.repository;

import br.com.lingua.liguadeouro.auth.model.Role;
import br.com.lingua.liguadeouro.core.repository.CoreJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CoreJpaRepository<Role, Long> {
}
