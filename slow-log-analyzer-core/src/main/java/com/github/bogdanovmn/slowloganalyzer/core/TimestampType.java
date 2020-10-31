package com.github.bogdanovmn.slowloganalyzer.core;

import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public enum TimestampType {
	DATETIME("yyyy-MM-dd HH:mm:ss", 2),
	TIME("HH:mm:ss", 1),
	SECONDS("ss", 1);

	DateTimeFormatter defaultPattern;
	int tokens;

	TimestampType(String defaultPattern, int tokens) {
		this.defaultPattern = DateTimeFormatter.ofPattern(defaultPattern);
		this.tokens = tokens;
	}
}
