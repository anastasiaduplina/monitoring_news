package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.model.KeyWord;
import org.example.repository.KeyWordRepository;
import org.example.service.NetworkService;
import org.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SomeFunctionsBeforeStart {
	@Autowired
	RoleService roleService;
	@Autowired
	NetworkService networkService;
	@Autowired
	KeyWordRepository keyWordRepository;
	public void addRoles(){
		log.info("addRoles");
		roleService.addRole("ROLE_USER");
		roleService.addRole("ROLE_ADMIN");
	}
	public void addNetwork(){
		log.info("add Networks");
		networkService.addNetwork("VK");
		KeyWord keyWord=new KeyWord();
		keyWord.setKeyWord("school");
		keyWord.setTrack(true);
		keyWordRepository.save(keyWord);
	}
}
