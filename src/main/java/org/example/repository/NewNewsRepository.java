package org.example.repository;

import org.example.model.KeyWord;
import org.example.model.NewNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewNewsRepository extends JpaRepository<NewNews,Long> {
	List<NewNews> findByKeyWord(KeyWord keyWord);
}
