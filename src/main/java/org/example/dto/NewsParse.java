package org.example.dto;

import lombok.Data;

import java.util.List;
@Data
public class NewsParse {
	String text;
	List<String> images;
	int time;
}
