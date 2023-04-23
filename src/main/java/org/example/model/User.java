package org.example.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Table(name="users")

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@Column(name = "login",unique = true)
	private String login;
	@NotBlank
	@Column(name = "password")
	private String password;
	@ManyToOne
	@JoinColumn(name = "id_role")
	private Role role;
//	@OneToMany(mappedBy = "user")
//	private List<Favourites> favourites;
}
