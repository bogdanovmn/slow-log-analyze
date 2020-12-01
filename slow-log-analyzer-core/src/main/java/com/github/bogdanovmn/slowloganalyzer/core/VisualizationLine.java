package com.github.bogdanovmn.slowloganalyzer.core;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class VisualizationLine {
	@NonNull
	String value;
	@NonNull
	VisualizationLineType type;
}
