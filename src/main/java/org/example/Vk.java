package org.example;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.exceptions.OAuthException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class Vk {

	Integer APP_ID=51624030;
	String CLIENT_SECRET="fAbrwJSwGWs4s5No7tMr";
	String REDIRECT_URI="http://localhost:8082/oauth";
	 Integer userId=285421600;
	 String accessToken="vk1.a.Ih_ONaHMl6wjTt8lj_6-pnt0zWVp-1qgi07DEqNUExfMKGp3NbOuYMFppbUzdJGhb7vZbFGKO-qlzccn-a00e4FuHIZYaiwHvCXBZEdYpyODoqrNTa24eA2K49gXlWQtB1dPVEJl_6Ud0GkCWJTn9HX92l27ZrOs1dH1WoKSJhLTeehb7mYIgtT-JdAi44z8cbhqJW7_deMQGlJVBw5_Jg";
	 TransportClient transportClient = new HttpTransportClient();
	VkApiClient vk = new VkApiClient(transportClient);
	UserActor actor;
	public  void auth(String code) throws ClientException, ApiException {

		log.info("okk"+code);
		UserAuthResponse authResponse = null;
		try {
			authResponse = vk.oAuth()

					.userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URI, code)
					.execute();
		} catch (OAuthException e) {
			e.getRedirectUri();
		}catch (Exception e){
			log.info(e.getMessage());
		}
		//log.info("userId: "+authResponse.getUserId()+", accessToken: "+authResponse.getAccessToken());
		actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
	}
	public void auth2(){
		actor = new UserActor(userId, accessToken);
	}
	public String getNews(String keyword, int count) throws ClientException, ApiException {
		if(actor!=null){
			log.info(keyword+" "+count);
			Object object=vk.newsfeed().search(actor).q(keyword).count(count).execute();
			return object.toString();
		}
		return  "[]";
	}
	public String getNewNews(String keyword, Integer last) throws ClientException, ApiException {
		if(last==null){
			if(actor!=null){
				log.info(keyword+" "+last);
				Object object=vk.newsfeed().search(actor).q(keyword).count(10).execute();
				return object.toString();
			}else{
				return "[]";
			}

		}else{
			if(actor!=null){
				log.info(keyword+" "+last);
				Object object=vk.newsfeed().search(actor).q(keyword).startTime(last).count(10).execute();
				return object.toString();
			}else{
				return "[]";
			}

		}


	}
}
