package org.example.service;

import org.example.model.Network;
import org.example.repository.NetworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NetworkService {
	@Autowired
	private NetworkRepository networkRepository;
	public void addNetwork(String name){
		Network network=new Network();
		network.setNetwork(name);
		networkRepository.save(network);

	}
}
