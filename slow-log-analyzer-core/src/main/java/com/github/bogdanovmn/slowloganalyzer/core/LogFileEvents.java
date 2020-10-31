package com.github.bogdanovmn.slowloganalyzer.core;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class LogFileEvents {
	private final TimestampDefinition timestampDefinition;
	private final List<LogEvent> events = new ArrayList<>();
	private final List<String> skippedMessages = new ArrayList<>();
	private int totalMessages;
	private int totalSkippedMessages;

	private static final int MAX_SKIPPED_MESSAGES_TO_STORE = 3;

	void add(String logRecord) {
		Optional<Integer> timestamp = timestampDefinition.parsedValue(logRecord);
		if (timestamp.isPresent()) {
			events.add(
				LogEvent.builder()
					.message(logRecord)
					.timestamp(timestamp.get())
					.build()
			);
		}
		else {
			if (totalSkippedMessages < MAX_SKIPPED_MESSAGES_TO_STORE) {
				skippedMessages.add(logRecord);
			}
			totalSkippedMessages++;
		}
		totalMessages++;
	}

	public List<SlowLogEvent> withDurationAboveThan(int threshold) {
		List<SlowLogEvent> result = new ArrayList<>();
		for (int i = 1; i < events.size(); i++) {
			long duration = events.get(i).timestamp() - events.get(i - 1).timestamp();
			if (duration > threshold) {
				result.add(
					SlowLogEvent.builder()
						.event(events.get(i - 1))
						.durationInSeconds(duration)
					.build()
				);
			}
		}
		return result;
	}

	public void printStatistic() {
		printStatistic(false);
	}

	public void printStatistic(boolean showSkipped) {
		System.out.printf(
			"%n[*] Log statistic:%nTotal lines: %d, events: %d, skipped: %d%n",
				totalMessages, events.size(), totalSkippedMessages
		);

		if (showSkipped) {
			System.out.printf("%n[*] Some of the skipped messages:%n");
			skippedMessages.forEach(System.out::println);
		}
	}
}
