package com.timedeal.api.service;

import com.timedeal.api.entity.Time;
import com.timedeal.api.http.request.TimeRequest;

public interface TimeUseCase {

	public Time save(TimeRequest request);

}
