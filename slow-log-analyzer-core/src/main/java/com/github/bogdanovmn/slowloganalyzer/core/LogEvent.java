package com.github.bogdanovmn.slowloganalyzer.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(of = "id")
public class LogEvent {
	int id;
	long timestamp;
	String message;
}
