package com.timedeal.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timedeal.api.entity.Time;
import com.timedeal.api.http.request.TimeRequest;
import com.timedeal.api.http.response.TimeResponse;
import com.timedeal.api.service.TimeUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/time")
@RequiredArgsConstructor
public class TimeController {

	private final TimeUseCase timeUseCase;
	
	@PostMapping
	public ResponseEntity<TimeResponse> save(@RequestBody TimeRequest request) throws IllegalAccessException {
		Time time = timeUseCase.save(request);
		return ResponseEntity.ok(new TimeResponse(time));
	}

	@PutMapping
	public ResponseEntity<TimeResponse> update(@RequestBody TimeRequest request) throws IllegalAccessException {
		Time time = timeUseCase.update(request);
		return ResponseEntity.ok(new TimeResponse(time));
	}
}