package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Request;
import org.example.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RequestService {
	@Autowired
	RequestRepository requestRepository;
	public void saveRequest(String uuid){
		Request request=requestRepository.findByUuidEquals(uuid);
		if(request!=null){
			request.setState("Done");
			requestRepository.save(request);
			log.info("done");
		}
	}
}
