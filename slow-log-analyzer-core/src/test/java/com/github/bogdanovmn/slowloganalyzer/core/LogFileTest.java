package com.github.bogdanovmn.slowloganalyzer.core;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LogFileTest {

	@Test
	public void eventsWithDurationAboveThan() throws FileNotFoundException {
		TimestampDefinition timestampDefinition = TimestampDefinition.builder()
			.type(TimestampType.DATETIME)
			.firstTokenPosition(1)
		.build();

		LogFileEvents events = new LogFile(
			getClass().getResource("/example-1.log").getPath()
		).events(timestampDefinition);

		List<SlowLogEvent> longEvents = events.withDurationAboveThan(3);

		assertEquals(
			4,
			longEvents.size()
		);

		longEvents.forEach(System.out::println);
		events.printStatistic(true);
	}

	@Test
	public void eventsWithDurationAboveThan2() throws FileNotFoundException {
		TimestampDefinition timestampDefinition = TimestampDefinition.builder()
			.type(TimestampType.TIME)
			.firstTokenPosition(1)
			.build();

		List<SlowLogEvent> events = new LogFile(
			getClass().getResource("/example-2.log").getPath()
		).events(timestampDefinition)
			.withDurationAboveThan(1);

		assertEquals(
			3,
			events.size()
		);

		events.forEach(System.out::println);
	}
}