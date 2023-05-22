package org.example.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="requests")
public class Request {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="uuid", unique = true)
	String uuid;
	@Column(name="state")
	String state;
}
