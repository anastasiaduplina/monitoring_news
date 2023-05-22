package org.example.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="news_from")
public class NewsFrom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;
	@ManyToOne
	@JoinColumn(name = "id_key_word")
	private KeyWord keyWord;
	@Column(name = "last_news")
	private String  lastNews;
}
