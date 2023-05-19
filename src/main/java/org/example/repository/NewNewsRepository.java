package org.example.repository;

import feign.Param;
import org.example.model.KeyWord;
import org.example.model.NewNews;
import org.example.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewNewsRepository extends JpaRepository<NewNews,Long> {
	List<NewNews> findByKeyWord(KeyWord keyWord);
//	@Query("select u.id,u.keyWord,u.news from NewNews u "+
//			"left join News  n on u.news.id=n.id " +
//			"where u.keyWord=:keyword "+
//			"group by u.id " +
//			"order by  n.timestamp desc ")
//	List<NewNews> findByKeyWordOOrderByNews(@Param("keyword") KeyWord keyWord);
}
