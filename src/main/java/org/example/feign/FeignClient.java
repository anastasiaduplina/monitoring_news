package org.example.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Client;
import org.example.dto.AddRole;

public interface FeignClient {

//	@RequestLine("GET /bank/feign")
//	void feign();
//
//	@RequestLine("POST /bank/api/addRole")
//	@Headers("Content-Type: application/json")
//	Long addRole(AddRole addRole);

	@RequestLine("GET /method/newsfeed.search?q=twitter&count=3&access_token=vk1.a.Ih_ONaHMl6wjTt8lj_6-pnt0zWVp-1qgi07DEqNUExfMKGp3NbOuYMFppbUzdJGhb7vZbFGKO-qlzccn-a00e4FuHIZYaiwHvCXBZEdYpyODoqrNTa24eA2K49gXlWQtB1dPVEJl_6Ud0GkCWJTn9HX92l27ZrOs1dH1WoKSJhLTeehb7mYIgtT-JdAi44z8cbhqJW7_deMQGlJVBw5_Jg&v=5.131")
	Object getVKNewsByKeyWord();
}
