package org.example.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
public class FindNews {
	@NotBlank
	String keyWord;
	@Max(100)
	Integer count;
	String network;
//	Timestamp dateFrom;
//	Timestamp dateTo;
	@NotBlank
	String uuid;
}
