package com.github.bogdanovmn.slowloganalyzer.core;

import com.github.bogdanovmn.humanreadablevalues.SecondsValue;

import java.util.ArrayList;
import java.util.List;

public class SlowLogEventsVisualization {
	private final List<SlowLogEvent> slowEvents;

	public SlowLogEventsVisualization(List<SlowLogEvent> slowEvents) {
		this.slowEvents = slowEvents;
	}

	public List<VisualizationLine> view() {
		List<VisualizationLine> result = new ArrayList<>();
		LogEvent lastEvent = null;
		for (SlowLogEvent slowEvent : slowEvents) {
			if (!slowEvent.event().equals(lastEvent)) {
				if (lastEvent != null) {
					result.add(
						VisualizationLine.builder()
							.value("")
							.type(VisualizationLineType.NEW_EVENT_SEPARATOR)
							.build()
					);
				}
				result.add(
					VisualizationLine.builder()
						.value(slowEvent.event().message())
						.type(VisualizationLineType.EVENT)
						.build()
				);
			}
			result.add(
				VisualizationLine.builder()
					.value(
						new SecondsValue(
							slowEvent.durationInSeconds()
						).fullString()
					)
					.type(VisualizationLineType.NOTE)
					.build()
			);
			result.add(
				VisualizationLine.builder()
					.value(
						slowEvent.nextEvent().message()
					)
					.type(VisualizationLineType.EVENT)
					.build()
			);
			lastEvent = slowEvent.nextEvent();
		}
		return result;
	}
}
