package com.github.bogdanovmn.slowloganalyzer.core;

import org.junit.Test;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TimestampDefinitionTest {

	@Test
	public void parsedValue() {
		TimestampDefinition tsd = TimestampDefinition.builder()
			.type(TimestampType.TIME)
			.firstTokenPosition(1)
			.pattern(DateTimeFormatter.ofPattern("HH:mm:ss"))
		.build();

		Optional<Integer> timestamp = tsd.parsedValue("00:10:01 message");
		assertTrue(
			timestamp.isPresent()
		);
		assertEquals(601, (long)timestamp.get());
	}

	@Test
	public void parsedValue2() {
		TimestampDefinition tsd = TimestampDefinition.builder()
			.type(TimestampType.TIME)
			.firstTokenPosition(2)
			.build();

		Optional<Integer> timestamp = tsd.parsedValue("text 00:10:01 message");
		assertTrue(
			timestamp.isPresent()
		);
		assertEquals(601, (long)timestamp.get());
	}

	@Test
	public void parsedValue3() {
		TimestampDefinition tsd = TimestampDefinition.builder()
			.type(TimestampType.DATETIME)
			.firstTokenPosition(1)
			.build();

		Optional<Integer> timestamp = tsd.parsedValue("2020-01-01 00:10:01 message");
		assertTrue(
			timestamp.isPresent()
		);
		assertEquals(601, (long)timestamp.get());
	}
}