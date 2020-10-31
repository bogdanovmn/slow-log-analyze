package com.github.bogdanovmn.slowloganalyzer.core;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Value
@Builder
public class TimestampDefinition {
	DateTimeFormatter pattern;
	@NonNull
	int firstTokenPosition;
	@NonNull
	TimestampType type;

	Optional<Integer> parsedValue(String logRecord) {
		String[] tokens = logRecord.split("\\s+");
		if (tokens.length < firstTokenPosition || tokens.length < type.tokens) {
			return Optional.empty();
		}
		else {
			String[] timestampTokens = new String[type.tokens];

			System.arraycopy(tokens, firstTokenPosition - 1, timestampTokens, 0, type.tokens);
			String timestamp = String.join(" ", timestampTokens);
			try {
				return Optional.of(
					LocalTime.parse(timestamp, pattern == null ? type.defaultPattern() : pattern)
						.toSecondOfDay()
				);
			} catch (DateTimeParseException ex) {
				return Optional.empty();
			}
		}
	}
}
