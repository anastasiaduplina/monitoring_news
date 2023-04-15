package org.example.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name="news")
public class News {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_network")
	private Network network;

	@Column(name = "content")
	private String content;

	@Column(name = "date")
	private Timestamp timestamp;

}
