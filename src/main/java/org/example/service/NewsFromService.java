package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.KeyWord;
import org.example.model.NewsFrom;
import org.example.model.User;
import org.example.repository.KeyWordRepository;
import org.example.repository.NewsFromRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NewsFromService {
	@Autowired
	NewsFromRepository newsFromRepository;
	@Autowired
	KeyWordRepository keyWordRepository;
	@Autowired
	UserRepository userRepository;
	public void saveUsersKeyword(String keyword,String login){
		log.info(login);
		KeyWord keyWord=keyWordRepository.findByKeyWord(keyword);
		User user=userRepository.findByLogin(login);
		NewsFrom news=new NewsFrom();
		news.setLastNews("0");
		news.setUser(user);
		news.setKeyWord(keyWord);
		newsFromRepository.save(news);


	}
}
