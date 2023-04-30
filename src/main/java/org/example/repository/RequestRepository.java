package org.example.repository;

import org.example.model.PopularNews;
import org.example.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request,Long> {
	Request findByUuidEquals(String uuid);
}
