package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.SomeFunctionsBeforeStart;
import org.example.dto.AddNetwork;
import org.example.dto.AddRole;
import org.example.dto.AddUser;
import org.example.dto.FindNews;
import org.example.model.News;
import org.example.service.RoleService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("")
@Validated
@Slf4j
public class Controller {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	//@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@Autowired
	SomeFunctionsBeforeStart functionsBeforeStart;
	@PostMapping("/start")
	public void start(){
		try{
			functionsBeforeStart.addRoles();
		}catch (Exception e){
			log.info(e.getMessage());
		}

	}
	@PostMapping("/addRole")
	public void addRole(@Valid @RequestBody AddRole addRole, Authentication auth) {
		Long t=roleService.addRole(addRole);
	}

	@PostMapping("/addUser")
	public void addUser(@RequestBody @Valid AddUser addUser, Authentication auth){
		addUser.setPassword(new BCryptPasswordEncoder().encode(addUser.getPassword()));
		userService.addUser(addUser);
	}
	@GetMapping("/getNews/filter")
	public List<News> getNewsByFilter(@RequestBody @Valid FindNews findNews, Authentication auth){
		return null;
	}
	@Secured({"ROLE_USER"})
	@PutMapping("/addToFavourite")
	public  void addToFavourite(@RequestBody Long id_news, Authentication auth){}
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/addNetwork")
	public void addNetwork(@RequestBody @Valid AddNetwork addNetwork, Authentication auth){}

	@GetMapping("/getPopularNews")
	public void getPopularNews(Authentication auth){}
}
