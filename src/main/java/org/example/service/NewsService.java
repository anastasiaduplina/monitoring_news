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
				newsParse.setTime(jsonObject.get("date").getAsInt());
				listOfNews.add(newsParse);

		 }
		Result result=new Result();
		result.setIdd(uuid);
		 Gson gson=new Gson();

		result.setNews(gson.toJson(listOfNews).toString());
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
