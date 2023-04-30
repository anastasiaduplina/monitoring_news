package org.example.model;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@Table(name="results")
@TypeDef(name = "jsonb", typeClass = com.vladmihalcea.hibernate.type.json.JsonBinaryType.class)
public class Result {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "idd")
	String idd;

	@Type(type = "jsonb")
	@Column(name="news",columnDefinition = "jsonb")
	String news;

}
