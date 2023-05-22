package org.example.controller;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.example.SomeFunctionsBeforeStart;
import org.example.Vk;
import org.example.dto.AddNetwork;
import org.example.dto.AddRole;
import org.example.dto.AddUser;
import org.example.feign.FeignClient;
import org.example.feign.OauthClient;
import org.example.model.User;
import org.example.service.RoleService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.beans.Encoder;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("")
@Validated
@Slf4j
public class UserController {
	private FeignClient feignClient;
	private OauthClient oauthClient;

	@Value("${orders.service.url:https://api.vk.com}")
	private String serviceUrl;
	@PostConstruct
	private void configure() {
		feignClient = Feign.builder()
				.encoder(new JacksonEncoder())
				.decoder(new JacksonDecoder())
				.target(FeignClient.class, serviceUrl);
	}
	@Autowired
	private Vk vk;

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	//@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@Autowired
	SomeFunctionsBeforeStart functionsBeforeStart;
	@GetMapping("/start")
	public RedirectView start(){
		log.info("hgjh");
//		oauthClient.getCode();
		try{
			functionsBeforeStart.addRoles();
			functionsBeforeStart.addNetwork();
		}catch (Exception e){
			log.info(e.getMessage());
		}
		RedirectView redirectView = new RedirectView();
		log.info("redirect");
		redirectView.setUrl("https://oauth.vk.com/authorize?client_id=51624030&display=page&redirect_uri=http://localhost:8082/oauth&scope=wall,offline&response_type=code");
		return redirectView;
	}
	@GetMapping("/oauth")//for start
	public String getCode(@RequestParam String code) throws ClientException, ApiException {
		try{
			functionsBeforeStart.addRoles();
			//functionsBeforeStart.addNetwork();
		}catch (Exception e){
			log.info(e.getMessage());
		}
		log.info("code");
		vk.auth("code");
		return "oauth ok!";
	}
	@PostMapping("/addRole")
	public void addRole(@Valid @RequestBody AddRole addRole, Authentication auth){
		Long t=roleService.addRole(addRole.getName());

	}


	@GetMapping("/addUser")
	public String addUser( String login, String password) throws UnsupportedEncodingException {
		//password=new BCryptPasswordEncoder().encode(password);
		AddUser addUser=new AddUser();
		addUser.setPassword(password);
		addUser.setLogin(login);
		User user=userService.addUser(addUser);
		return "ok\n login: "+user.getLogin()+"\npassword: "+user.getPassword();
	}
	@GetMapping("/authorization")
	public String auth(String password,String login){
		if(userService.checkUser(password,login)){
			return "ok";
		}else {
			return "something wrong";
		}
	}
	@GetMapping("/letters")
	public String lettrs() throws UnsupportedEncodingException {
		return URLEncoder.encode("Привет!", "UTF-8");
	}
	@GetMapping("/change/password")
	public String changePassword(String login,String passwordOld,String passwordNew){
		if(userService.checkUser(passwordOld,login)){
			User user=userService.changePassword(login,passwordNew);
			return "ok\n login: "+user.getLogin()+"\npassword: "+user.getPassword();
		}else {
			return "something wrong";
		}
	}


}
