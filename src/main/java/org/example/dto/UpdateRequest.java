package org.example.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateRequest {
	@NotBlank
	public String uuid;
	@NotBlank
	public String login;
}
