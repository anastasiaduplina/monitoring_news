package org.example.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.MakeTrack;
import org.example.repository.KeyWordRepository;
import org.example.service.KeyWordService;
import org.example.service.NewsFromService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("")
@Validated
@Slf4j
public class KeyWordController {
	@Autowired
	KeyWordService keyWordService;
	@Autowired
	NewsFromService newsFromService;
	Gson gson=new Gson();


	@GetMapping("/addKeyword")
	public void addKeyword( String keyword,String login){
		keyWordService.addKeyword(keyword);
		keyWordService.makeTrack(keyword, true);

		newsFromService.saveUsersKeyword(keyword, login);

	}
	@GetMapping("/getAllKeywords")
	public String getAllKeywords(String login) throws UnsupportedEncodingException {
		return URLEncoder.encode(gson.toJson(newsFromService.getAllKeywords(login)),"UTF-8");
	}
//	@PostMapping("/makeTrack")
//	public void makeTrack(@RequestBody @Valid MakeTrack makeTrack){
//		keyWordService.makeTrack(makeTrack.keyword, makeTrack.track);
//	}
	@DeleteMapping("/deleteKeyword")
	public void deleteKeyword(@RequestBody String keyword){
		keyWordService.deleteKeyword(keyword);
	}
}
