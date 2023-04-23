package org.example.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddRole {

	@NotBlank
	String name;
}