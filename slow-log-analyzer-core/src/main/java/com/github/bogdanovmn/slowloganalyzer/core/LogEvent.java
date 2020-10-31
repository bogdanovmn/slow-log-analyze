package com.github.bogdanovmn.slowloganalyzer.core;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LogEvent {
	long timestamp;
	String message;
}
