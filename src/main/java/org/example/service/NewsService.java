package org.example.service;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import liquibase.pro.packaged.A;
import lombok.extern.slf4j.Slf4j;
import org.example.Vk;
import org.example.dto.FindNews;
import org.example.model.Request;
import org.example.model.Result;
import org.example.repository.NewsRepository;
import org.example.repository.RequestRepository;
import org.example.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class NewsService {
//	@Autowired
//	private NewsRepository newsRepository;
	@Autowired
	private ResultRepository resultRepository;
	@Autowired
	private RequestRepository requestRepository;

	@Autowired
	Vk vk;
	 public void saveNews(String uuid, FindNews findNews) throws ClientException, ApiException {
		 Object news=vk.getNews(findNews.getKeyWord(),findNews.getCount());
		Result result=new Result();
		result.setIdd(uuid);
		List<Object> list=new ArrayList<>();
		list.add(news);
		result.setNews(news.toString());
		log.info(news.toString());
		resultRepository.save(result);
		 Request request=requestRepository.findByUuidEquals(uuid);
		 if(request!=null){

			 request.setState("Done");
			 requestRepository.save(request);
			 log.info("done");
		 }

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
	 public int setState(String uuid){
		 int k=1;
		 Request request = new Request();
		 request.setState("NotDone");
		 request.setUuid(uuid);
		 try{
			 requestRepository.save(request);
		 }catch (Exception e){
			 k=0;
			 log.warn(e.getMessage());
		 }
		 return k;
	 }
}
