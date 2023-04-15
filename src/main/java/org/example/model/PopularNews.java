package org.example.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="popular_news")
public class PopularNews {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "id_key_word")
	private KeyWord keyWord;

	@ManyToOne
	@JoinColumn(name = "id_news")
	private News news;
}
