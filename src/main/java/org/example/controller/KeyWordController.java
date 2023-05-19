package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.MakeTrack;
import org.example.repository.KeyWordRepository;
import org.example.service.KeyWordService;
import org.example.service.NewsFromService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("")
@Validated
@Slf4j
public class KeyWordController {
	@Autowired
	KeyWordService keyWordService;
	@Autowired
	NewsFromService newsFromService;


	@PostMapping("/addKeyword")
	public void addKeyword(@RequestBody @Valid MakeTrack makeTrack){
		keyWordService.addKeyword(makeTrack.keyword);
		keyWordService.makeTrack(makeTrack.keyword, true);

		newsFromService.saveUsersKeyword(makeTrack.keyword, makeTrack.login);

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
