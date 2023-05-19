package org.example.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.NewsParse;
import org.example.model.Request;
import org.example.model.Result;
import org.example.repository.RequestRepository;
import org.example.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ResultService {
	@Autowired
	ResultRepository resultRepository;
	@Autowired
	RequestRepository requestRepository;
	public void saveResult(String uuid, List<NewsParse> listOfNews){
		Result result=new Result();
		result.setIdd(uuid);
		Gson gson=new Gson();
		result.setNews(gson.toJson(listOfNews));
		resultRepository.save(result);
	}
	public String checkNewsResult(String uuid){
		Request request=requestRepository.findByUuidEquals(uuid);
		String answer="not done";
		if(request!=null){
			if(Objects.equals(request.getState(), "Done")){
				Result result=resultRepository.findByIddEquals(uuid);
				if(result!=null){
					answer=result.getNews();
					resultRepository.delete(result);
					requestRepository.delete(request);
				}
			}
		}
		return answer;
	}
}
