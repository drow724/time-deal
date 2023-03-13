package com.timedeal.common.util;

import java.time.LocalDateTime;

public class LocalDateTimeUtil {

	public static LocalDateTime now() {
		LocalDateTime now = LocalDateTime.now();
		
		LocalDateTime ldt = LocalDateTime.of(now.getYear(),
		now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute(), now.getSecond());
		return ldt;
	}
}
