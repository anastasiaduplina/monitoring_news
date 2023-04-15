package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AddNetwork;
import org.example.dto.AddUser;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/monitoring")
@Validated
@Slf4j
public class Controller {
	@Autowired
	private UserService userService;
	//users
	@GetMapping("/addUser")
	public String addUser(//@RequestBody @Valid AddUser addUser){}
	){
		return "OK";
	}

	//networks
	@PostMapping("/addNetwork")
	public void addNetwork(@RequestBody @Valid AddNetwork addNetwork){}
}
