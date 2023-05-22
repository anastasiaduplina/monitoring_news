package org.example.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="networks")
public class Network {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "network",unique = true)
	private String network;

}
