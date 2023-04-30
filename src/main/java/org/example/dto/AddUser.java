package org.example.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddUser {
	@NotBlank
	String login;
	@NotBlank
	String password;
	String role;

}
