package org.example.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
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
	                     @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException, ClientException, ApiException {
		FindNews findNews=gson.fromJson(text, FindNews.class);
		try{
			newsService.saveNews(findNews.getUuid(), findNews);
			channel.basicAck(tag, false);
			log.info("Acknowledge");
		}catch (Exception e){
			channel.basicReject(tag, true);
			log.info("Rejecting message");
		}

	}
}
