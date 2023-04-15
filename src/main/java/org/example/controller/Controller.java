package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AddNetwork;
import org.example.dto.AddUser;
import org.example.dto.FindNews;
import org.example.model.News;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/monitoring")
@Validated
@Slf4j
public class Controller {
	@Autowired
	private UserService userService;

	//чуть позже добавлю spring security
	@PostMapping("/addUser")
	public void addUser(@RequestBody @Valid AddUser addUser){}
	@GetMapping("/getNews/filter")
	public List<News> getNewsByFilter(@RequestBody @Valid FindNews findNews){
		return null;
	}
	@PutMapping("/addToFavourite")
	public  void addToFavourite(@RequestBody Long id_news){}
	@PostMapping("/addNetwork")
	public void addNetwork(@RequestBody @Valid AddNetwork addNetwork){}

	@GetMapping("/getPopularNews")
	public void getPopularNews(){}
}
