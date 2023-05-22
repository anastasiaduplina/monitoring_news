package org.example.rabbitmq;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.FindNews;
import org.example.service.NewsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class Receiver {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private NewsService newsService;

	Gson gson=new Gson();

	@RabbitListener(queues = "news", ackMode = "MANUAL")
	public void receive1(String text, MessageHeaders headers, Channel channel,
	                     @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
		FindNews findNews=gson.fromJson(text, FindNews.class);
		log.info("findNews: "+findNews.toString());
		int i = 0;
		if(headers.containsKey("rejectingCount")){
			i =  Integer.parseInt(headers.get("rejectingCount").toString());
		}
		if(i<5){
			try{
				newsService.saveNews(findNews.getUuid(), findNews);

				channel.basicAck(tag, false);
				log.info("Acknowledge");
			}catch (Exception e){
				log.info(e.getMessage());
				i++;
				int finalI = i;
				rabbitTemplate.convertAndSend("news",text, m->{
					m.getMessageProperties().setHeader("rejectingCount",finalI+"");
					return m;
				});
				channel.basicAck(tag, false);
				log.info("Rejecting message:"+ finalI+" error:"+e.getMessage());
			}
		}else{
			log.info("too many rejecting");
			channel.basicAck(tag, false);
		}


	}
}
