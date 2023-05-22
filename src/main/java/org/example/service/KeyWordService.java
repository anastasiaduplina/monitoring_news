package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.KeyWord;
import org.example.model.NewsFrom;
import org.example.repository.KeyWordRepository;
import org.example.repository.NewsFromRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class KeyWordService {
	@Autowired
	KeyWordRepository keyWordRepository;
	@Autowired
	private NewsFromRepository newsFromRepository;

	public void addKeyword(String keyword){
		KeyWord keyWord=new KeyWord();
		keyWord.setKeyWord(keyword);
		keyWord.setTrack(false);
		keyWordRepository.save(keyWord);
	}
	public void makeTrack(String keyword,boolean track){
		KeyWord keyWord=keyWordRepository.findByKeyWord(keyword);
		if(keyWord!=null){
			keyWord.setTrack(track);
			keyWordRepository.save(keyWord);
		}
	}
	public void deleteKeyword(String keyword){
		KeyWord keyWord=keyWordRepository.findByKeyWord(keyword);
		List<NewsFrom> news=newsFromRepository.findByKeyWord(keyWord);
		newsFromRepository.deleteAll(news);
		log.info("DELETE");
		if(keyWord!=null){
			log.info("del");
			keyWordRepository.delete(keyWord);
		}
	}

}
