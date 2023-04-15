package org.example.repository;

import org.example.model.PopularNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopularNewsRepository extends JpaRepository<PopularNews,Long> {
}
