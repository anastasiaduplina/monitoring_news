package org.example.repository;

import org.example.model.KeyWord;
import org.example.model.Network;
import org.example.model.NewNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NetworkRepository extends JpaRepository<Network,Long> {
	Network findByNetwork(String network);
}
