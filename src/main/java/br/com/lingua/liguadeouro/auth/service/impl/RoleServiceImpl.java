package br.com.lingua.liguadeouro.auth.service.impl;

import br.com.lingua.liguadeouro.auth.model.Role;
import br.com.lingua.liguadeouro.auth.repository.RoleRepository;
import br.com.lingua.liguadeouro.auth.service.RoleService;
import br.com.lingua.liguadeouro.core.service.JpaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoleServiceImpl extends JpaService<Role, Long, RoleRepository>
        implements RoleService {

    public RoleServiceImpl(RoleRepository repository) {
        super(repository, Role.class);
    }


}


