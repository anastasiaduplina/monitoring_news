package org.example.repository;

import org.example.model.KeyWord;
import org.example.model.News;
import org.example.model.NewsFrom;
import org.example.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News,Long> {
	//List<News> findByName(String role_user);
	///List<News> findByKeyWord(KeyWord keyWord);
}
