package org.example;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.example.model.KeyWord;
import org.example.repository.KeyWordRepository;
import org.example.repository.UserRepository;
import org.example.service.NetworkService;
import org.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SomeFunctionsBeforeStart {
	@Autowired
	RoleService roleService;
	@Autowired
	private Vk vk;
	@Autowired
	NetworkService networkService;
	@Autowired
	KeyWordRepository keyWordRepository;
	@Autowired
	UserRepository userRepository;

	@EventListener(ApplicationReadyEvent.class)
	public void addRoles(){
		log.info("addRoles");
		roleService.addRole("ROLE_USER");
		roleService.addRole("ROLE_ADMIN");
	}
	@EventListener(ApplicationReadyEvent.class)
	public void addNetwork(){
		log.info("add Networks");
		networkService.addNetwork("VK");
		KeyWord keyWord=new KeyWord();
		keyWord.setKeyWord("le sserafim");
		keyWord.setTrack(true);
		keyWordRepository.save(keyWord);
	}
	@EventListener(ApplicationReadyEvent.class)
	public void vkAuth() throws ClientException, ApiException {
		vk.auth("code");
	}
}
