package com.timedeal.api.http.response;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.timedeal.api.entity.Time;

import lombok.Getter;

@Getter
public class TimeResponse {

	private Long id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	@DateTimeFormat(pattern = "HH:mm:ss")
	private LocalTime time;
	
	public TimeResponse(Time time) {
		this.id = time.getId();
		this.date = time.getDate();
		this.time = time.getTime();
	}
}
