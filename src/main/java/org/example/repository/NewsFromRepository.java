package org.example.repository;

import org.example.model.KeyWord;
import org.example.model.NewsFrom;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsFromRepository extends JpaRepository<NewsFrom,Long> {
	List<NewsFrom> findByUser(User user);
	NewsFrom findByKeyWordAndUser(KeyWord keyWord, User user);
	List<NewsFrom> findByKeyWord(KeyWord keyWord);
}
