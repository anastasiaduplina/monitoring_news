package org.example.repository;

import org.example.model.Network;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NetworkRepository extends JpaRepository<Network,Long> {
	Network findByNetwork(String network);
}
