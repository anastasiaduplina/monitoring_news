package org.example.service;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AddRole;
import org.example.dto.AddUser;
import org.example.feign.FeignClient;
import org.example.model.Role;
import org.example.model.User;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;


	public Long addUser(AddUser addUser){
		User user=new User();
		user.setLogin(addUser.getLogin());
		user.setPassword(addUser.getPassword());
		if(addUser.getRole()==null){
			user.setRole(roleRepository.findByName("ROLE_USER"));
		}else {
			try{
				user.setRole(roleRepository.findByName(addUser.getRole()));
			}catch (Exception e){
				log.error(e.getMessage());
				user.setRole(roleRepository.findByName("ROLE_USER"));
			}

		}
		userRepository.save(user);
		return 0L;
	}


}
