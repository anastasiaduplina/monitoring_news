package org.example.repository;

import org.example.model.Request;
import org.example.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Result,Long> {
	Result findByIddEquals(String idd);
}
