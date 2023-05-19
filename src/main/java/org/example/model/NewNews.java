package org.example.model;

import lombok.Data;
import org.example.dto.NewsParse;

import javax.persistence.*;

@Data
@Entity
@Table(name="new_news")
public class NewNews {
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
