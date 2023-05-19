package org.example.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MakeTrack {
	@NotBlank
	public  String keyword;
	@NotNull
	public  String login;
}
