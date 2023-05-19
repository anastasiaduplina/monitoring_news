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
	 String accessToken=" vk1.a.KhyE8ODo4qtDuxwZpY0gI-LarCkAoK_eewwh_M1ucUGykHHNFDBZF-txaWHQE-ibbfeXBaI9dvSjDNN007lmognNemyVx0l3q-M5EDdCwSA2OItet61g__RVOFbMfyQwyNjWeFzZ0HuQFBmAU7Ff1au8E-Qv8KGJKWo0y90rNtGdAP1a-S-2SUUc6wmh2BsBRcWG3OQAvkYijiSD37rd4A";
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
		log.info("userId: "+authResponse.getUserId()+", accessToken: "+authResponse.getAccessToken());
		actor = new UserActor(userId, authResponse.getAccessToken());
	}
	public String getNews(String keyword, int count) throws ClientException, ApiException {
		//if(actor!=null){
			log.info(keyword+" "+count);
			Object object=vk.newsfeed().search(actor).q(keyword).count(count).execute();
			log.info("vk! "+object.toString());
			return object.toString();
		//}
		//return  null;
	}
	public String getNewNews(String keyword, String last) throws ClientException, ApiException {
		if(last==null){
			log.info(keyword+" "+last);
			Object object=vk.newsfeed().search(actor).q(keyword).count(200).execute();
			log.info("vk! "+object.toString());
			return object.toString();
		}else{
			log.info(keyword+" "+last);
			Object object=vk.newsfeed().search(actor).q(keyword).startFrom(last).count(200).execute();
			log.info("vk! "+object.toString());
			return object.toString();
		}


	}
}
