package org.example.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.example.Vk;
import org.example.dto.NewsParse;
import org.example.model.*;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NewNewsService {
	@Autowired
	NewNewsRepository newNewsRepository;
	@Autowired
	KeyWordRepository keyWordRepository;
	@Autowired
	NewsRepository newsRepository;
	@Autowired
	NewsFromRepository newsFromRepository;
	@Autowired
	Vk vk;
	@Autowired
	private NetworkRepository networkRepository;
	@Autowired
	private UserRepository userRepository;
	Gson gson = new Gson();

	@Scheduled(fixedDelay = 15 * 1000)
	public void saveNews() throws ClientException, ApiException, InterruptedException {
		log.info("saveNewNews");
		List<KeyWord> keyWords = keyWordRepository.findAll();//list of keywords
		for (KeyWord key : keyWords) {//go on keywords
			if (key.isTrack()) {//if keywords is track
				log.info("key:" + key.getKeyWord());
				Integer last = null;
				if (key.getLastNews() != null) {
					last = Integer.valueOf(key.getLastNews());//get last news
				}
				List<NewsParse> list = new ArrayList<>();
				Object news = vk.getNewNews(key.getKeyWord(), last);
				log.info("AAAAAAAA"+news.toString());
				list = parseNewsFromVk(news);
				if(last==null){
					last=0;
				}
				Long last2= Long.valueOf(last);
				for (NewsParse item : list) {
					log.info("Time: "+item.getTime()+", "+last);
					if(item.getTime()>last){
						try {//try save news and new news
							News el = new News();
							el.setContent(gson.toJson(item));
							el.setNetwork(networkRepository.findByNetwork("VK"));
							el.setTimestamp(new Timestamp(item.getTime()));
							last2 = Math.max(last2,item.getTime());
							newsRepository.save(el);
							NewNews newEl = new NewNews();
							newEl.setKeyWord(key);
							newEl.setNews(el);
							//log.info("newNews: "+newEl.toString());
							newNewsRepository.save(newEl);

						} catch (Exception e) {
							log.info(e.getMessage());
						}
					}


				}
				key.setLastNews(last2.toString());
				keyWordRepository.save(key);
				Thread.sleep(1000);
			}

		}
	}

	public List<NewsParse> getNews(String keyword, String login) {
		log.info("GETNEWS");
		KeyWord keyWord = keyWordRepository.findByKeyWord(keyword);//get keyword
		List<NewNews> listOfNews = newNewsRepository.findByKeyWord(keyWord);//get news by keyword
		User user = userRepository.findByLogin(login);//get user
		log.info(keyword,user.toString());
		NewsFrom last = newsFromRepository.findByKeyWordAndUser(keyWord, user);//get last news for user
		if(last==null){
			return  null;
		}
		List<NewsParse> listResult = new ArrayList<>();//list with result
		Long last2=Long.parseLong(last.getLastNews());
		List<NewsParse> newList=new ArrayList<>();
		for (NewNews item : listOfNews) {
			String h = newsRepository.findById(item.getNews().getId()).get().getContent();//get news comtent by id
			NewsParse newsParse = gson.fromJson(h, NewsParse.class);
			newsParse.setNew(item.getNews().getTimestamp().compareTo(new Timestamp(Long.parseLong(last.getLastNews()))) > 0);//comparing times
			if(newsParse.isNew()){
				newList.add(newsParse);
				last2=Math.max(last2,newsParse.getTime());
			}
			listResult.add(newsParse);
		}
		last.setLastNews(last2.toString());
		newsFromRepository.save(last);

		return newList;
	}

	List<NewsParse> parseNewsFromVk(Object news) {
		final String jsonString = news.toString();
		final JsonParser parser = new JsonParser();
		final JsonObject root = parser.parse(jsonString).getAsJsonObject();
		//log.info("newsserver " + news.toString());
		JsonArray array = root.getAsJsonArray("items");
		List<NewsParse> listOfNews = new ArrayList<>();
		for (int i = 0; i < array.size(); i++) {
			//log.info("i: " + i);
			NewsParse newsParse = new NewsParse();
			JsonObject jsonObject = array.get(i).getAsJsonObject();
			newsParse.setText(jsonObject.get("text").getAsString());
			JsonArray array2 = jsonObject.getAsJsonArray("attachments");
			List<String> list = new ArrayList<>();
			for (int j = 0; j < array2.size(); j++) {
				if (array2.get(j).getAsJsonObject().get("type").getAsString().equals("photo")) {
					JsonArray arr3 = array2.get(j).getAsJsonObject().getAsJsonObject("photo").getAsJsonArray("sizes");
					list.add(arr3.get(arr3.size() - 1).getAsJsonObject().get("url").getAsString());
				}
			}
			newsParse.setImages(list);
			newsParse.setTime(jsonObject.get("date").getAsLong());
			newsParse.setNew(true);
			listOfNews.add(newsParse);

		}
		return listOfNews;
	}

}
