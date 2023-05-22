package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name="news")
@TypeDef(name = "jsonb", typeClass = com.vladmihalcea.hibernate.type.json.JsonBinaryType.class)
public class News {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_network")
	private Network network;
	@Type(type = "jsonb")
	@Column(name = "content",columnDefinition = "jsonb",unique = true)
	private String content;

	@Column(name = "date")
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="Europe/Berlin")
	private Timestamp timestamp;

}
