package com.github.bogdanovmn.slowloganalyzer.core;

import com.github.bogdanovmn.humanreadablevalues.SecondsValue;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SlowLogEvent {
	long durationInSeconds;
	@NonNull
	LogEvent event;
	@NonNull
	LogEvent nextEvent;

	@Override
	public String toString() {
		return String.format(
			"[%s] %s",
				new SecondsValue(durationInSeconds).fullString(),
				event.message()
		);
	}
}
