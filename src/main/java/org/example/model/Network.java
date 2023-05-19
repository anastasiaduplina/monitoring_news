package org.example.model;

import liquibase.pro.packaged.O;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

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
