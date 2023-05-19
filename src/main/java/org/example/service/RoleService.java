package org.example.service;

import lombok.Data;
import org.example.dto.AddRole;
import org.example.model.Role;
import org.example.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;
	public Long addRole(String name) {
		Role role=new Role();
		role.setName(name);
		roleRepository.save(role);
		return 0L;
	}
}
