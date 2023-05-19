package org.example.repository;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.example.model.KeyWord;
import org.example.model.NewsFrom;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsFromRepository extends JpaRepository<NewsFrom,Long> {
	NewsFrom findByKeyWord(KeyWord keyWord);
	NewsFrom findByKeyWordAndUser(KeyWord keyWord, User user);
}
