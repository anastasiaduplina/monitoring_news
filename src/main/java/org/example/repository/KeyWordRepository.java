package org.example.repository;

import org.example.model.KeyWord;
import org.example.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyWordRepository extends JpaRepository<KeyWord,Long> {
	KeyWord findByKeyWord(String keyword);
}
