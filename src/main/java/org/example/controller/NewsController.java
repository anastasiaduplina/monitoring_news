package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.example.SomeFunctionsBeforeStart;
import org.example.dto.AddNetwork;
import org.example.dto.FindNews;
import org.example.dto.NewsParse;
import org.example.service.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("")
@Validated
@Slf4j
public class NewsController {

	@Autowired
	private NetworkService networkService;

	@Autowired
	private RabbitTemplate rabbitTemplate;
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
	@GetMapping("/getNews/filter")
	public String getNewsByFilter(String keyword,String count,String uuid) throws  JsonProcessingException {
		log.info("keyword:"+keyword);
		int k=newsService.setState(uuid);
		if(k==1){
			FindNews findNews=new FindNews();
			findNews.setCount(Integer.valueOf(count));
			findNews.setKeyWord(keyword);
			findNews.setUuid(uuid);
			rabbitTemplate.convertAndSend("news",mapper.writeValueAsString(findNews));
			return "ok";
		}

		return "something wrong";
	}
	@GetMapping("/request/getNewsByFilter")//получение ответа сервера о готовности запроса
	public String getRNews(String uuid) throws UnsupportedEncodingException {
		return URLEncoder.encode(resultService.checkNewsResult(uuid),"UTF-8");

	}

	@Secured({"ROLE_ADMIN"})
	@PostMapping("/addNetwork")
	public void addNetwork(@RequestBody @Valid AddNetwork addNetwork){
		networkService.addNetwork(addNetwork.getNetwork());
	}
	@GetMapping("/getNewNews")
	public String getNewNews( String keyword, String login) throws UnsupportedEncodingException {
		log.info("NEWNEWS "+keyword+" "+login);
		List<NewsParse> list=newNewsService.getNews(keyword, login);
		if(list==null){
			return "null";
		}
		return URLEncoder.encode(gson.toJson(list),"UTF-8");

	}
}
