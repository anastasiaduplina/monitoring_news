package org.example.service;

import org.example.model.KeyWord;
import org.example.repository.KeyWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyWordService {
	@Autowired
	KeyWordRepository keyWordRepository;
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
		if(keyWord!=null){
			keyWordRepository.delete(keyWord);
		}
	}
}
