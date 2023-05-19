package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.example.SomeFunctionsBeforeStart;
import org.example.Vk;
import org.example.dto.AddNetwork;
import org.example.dto.FindNews;
import org.example.dto.UpdateRequest;
import org.example.feign.FeignClient;
import org.example.service.*;
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
	@Autowired
	private NetworkService networkService;
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
	@Autowired
	private ResultService resultService;
	@Autowired
	private NewNewsService newNewsService;
	Gson gson=new Gson();

	ObjectMapper mapper = new ObjectMapper();
	@Autowired
	SomeFunctionsBeforeStart functionsBeforeStart;
	@PutMapping("/getNews/filter")
	public String getNewsByFilter(@RequestBody @Valid FindNews findNews) throws  JsonProcessingException {
		int k=newsService.setState(findNews.getUuid());
		if(k==1){
			rabbitTemplate.convertAndSend("news",mapper.writeValueAsString(findNews));
			return "ok";
		}

		return "something wrong";
	}
	@PutMapping("/request/getNewsByFilter")//получение ответа сервера о готовности запроса
	public String getRNews(@RequestBody UpdateRequest updateRequest){
		return resultService.checkNewsResult(updateRequest.uuid);

	}

	@Secured({"ROLE_ADMIN"})
	@PostMapping("/addNetwork")
	public void addNetwork(@RequestBody @Valid AddNetwork addNetwork, Authentication auth){
		networkService.addNetwork(addNetwork.getNetwork());
	}
	//@Secured({"ROLE_USER"})
	@PutMapping("/getNewNews")
	public String getNewNews(@RequestBody String keyword, String login,Authentication auth){
		return gson.toJson(newNewsService.getNews(keyword, login));

	}
	@Secured({"ROLE_USER"})
	@PutMapping("/lookedUpNews")
	public void lookedUpNews(@RequestBody String lastTime,Authentication auth){

	}
}
