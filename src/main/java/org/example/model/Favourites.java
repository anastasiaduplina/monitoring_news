package org.example.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name="favourites")
public class Favourites {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name="id_user")
	private User user;
	@ManyToOne
	@JoinColumn(name="id_news")
	private News news;
	@ManyToOne
	@JoinColumn(name = "id_key_word")
	private KeyWord keyWord;

}
