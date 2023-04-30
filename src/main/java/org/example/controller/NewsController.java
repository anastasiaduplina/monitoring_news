package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.example.SomeFunctionsBeforeStart;
import org.example.Vk;
import org.example.dto.AddNetwork;
import org.example.dto.FindNews;
import org.example.feign.FeignClient;
import org.example.service.NewsService;
import org.example.service.RoleService;
import org.example.service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RestController
@RequestMapping("")
@Validated
@Slf4j
public class NewsController {
	private FeignClient feignClient;

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
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private NewsService newsService;

	ObjectMapper mapper = new ObjectMapper();
	//@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@Autowired
	SomeFunctionsBeforeStart functionsBeforeStart;
	@PutMapping("/getNews/filter")
	public String getNewsByFilter(@RequestBody @Valid FindNews findNews) throws ClientException, ApiException, JsonProcessingException {
		int k=newsService.setState(findNews.getUuid());
		if(k==1){
			rabbitTemplate.convertAndSend("news",mapper.writeValueAsString(findNews));
			return "ok";
		}

		return "something wrong";
	}
	@PutMapping("/request/getNewsByFilter")
	public String getRNews(@RequestBody String uuid){
		return newsService.checkNewsResult(uuid);

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
