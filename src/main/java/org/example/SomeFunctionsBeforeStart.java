package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.AddRole;
import org.example.model.Role;
import org.example.repository.RoleRepository;
import org.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SomeFunctionsBeforeStart {
	@Autowired
	RoleService roleService;
	public void addRoles(){
		log.info("addRoles");
		AddRole role=new AddRole();
		role.setName("ROLE_USER");
		roleService.addRole(role);
		role.setName("ROLE_ADMIN");
		roleService.addRole(role);
	}
}
