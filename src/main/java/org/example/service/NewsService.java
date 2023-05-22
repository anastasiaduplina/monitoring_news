package org.example.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import liquibase.pro.packaged.A;
import lombok.extern.slf4j.Slf4j;
import org.example.Vk;
import org.example.dto.FindNews;
import org.example.dto.NewsParse;
import org.example.model.Network;
import org.example.model.News;
import org.example.model.Request;
import org.example.model.Result;
import org.example.repository.NetworkRepository;
import org.example.repository.NewsRepository;
import org.example.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class NewsService {
	@Autowired
	private ResultService resultService;
	@Autowired
	private RequestService requestService;
	@Autowired
	private RequestRepository requestRepository;
	@Autowired
	Vk vk;
	 public void saveNews(String uuid, FindNews findNews) throws ClientException, ApiException {
		 log.info("saving news");
		 Object news=vk.getNews(findNews.getKeyWord(),findNews.getCount());
		 //log.info(news.toString());
		 List<NewsParse> listOfNews=parseNewsFromVk(news);
		 resultService.saveResult(uuid,listOfNews);
		 requestService.saveRequest(uuid);

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
	 List<NewsParse> parseNewsFromVk(Object news){
		 final String jsonString = news.toString();
		 final JsonParser parser = new JsonParser();
		 final JsonObject root = parser.parse(jsonString).getAsJsonObject();
		 log.info("newsserver "+news.toString());
		 JsonArray array = root.getAsJsonArray("items");
		 List<NewsParse> listOfNews=new ArrayList<>();
		 for(int i=0;i<array.size();i++){
			 log.info("i: "+i);
			 NewsParse newsParse=new NewsParse();
			 JsonObject jsonObject= array.get(i).getAsJsonObject();
			 newsParse.setText(jsonObject.get("text").getAsString());
			 JsonArray array2=jsonObject.getAsJsonArray("attachments");
			 List<String> list=new ArrayList<>();
			 for(int j=0;j<array2.size();j++){
				 if(array2.get(j).getAsJsonObject().get("type").getAsString().equals("photo")){
					 JsonArray arr3=array2.get(j).getAsJsonObject().getAsJsonObject("photo").getAsJsonArray("sizes");
					 list.add(arr3.get(arr3.size()-1).getAsJsonObject().get("url").getAsString());
				 }
			 }
			 newsParse.setImages(list);
			 newsParse.setTime(jsonObject.get("date").getAsLong());
			 listOfNews.add(newsParse);

		 }
		 return listOfNews;
	 }
}
