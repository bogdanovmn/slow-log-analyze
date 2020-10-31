package com.github.bogdanovmn.slowloganalyzer.core;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SlowLogEvent {
	long durationInSeconds;
	LogEvent event;

	@Override
	public String toString() {
		return String.format(
			"[%d sec] %s", durationInSeconds, event.message()
		);
	}
}
