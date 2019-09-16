package br.com.lingua.liguadeouro.auth.repository;

import br.com.lingua.liguadeouro.auth.model.User;
import br.com.lingua.liguadeouro.core.repository.CoreJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CoreJpaRepository<User, Long> {

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);
}
