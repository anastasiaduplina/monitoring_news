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

		actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
	}
	public String getNews(String keyword, int count) throws ClientException, ApiException {
		log.info(keyword+" "+count);
		Object object=vk.newsfeed().search(actor).q(keyword).count(count).execute();
		log.info("vk! "+object.toString());
		return object.toString();
	}
}
