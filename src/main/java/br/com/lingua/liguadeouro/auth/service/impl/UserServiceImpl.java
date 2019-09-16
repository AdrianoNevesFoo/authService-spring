package br.com.lingua.liguadeouro.auth.service.impl;

import br.com.lingua.liguadeouro.auth.model.User;
import br.com.lingua.liguadeouro.core.service.JpaService;
import br.com.lingua.liguadeouro.auth.repository.UserRepository;
import br.com.lingua.liguadeouro.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl extends JpaService<User, Long, UserRepository>
        implements UserService {

    public UserServiceImpl(UserRepository repository) {
        super(repository, User.class);
    }


}


