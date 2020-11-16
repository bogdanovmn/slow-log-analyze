package com.github.bogdanovmn.slowloganalyzer.cli;

import com.github.bogdanovmn.cmdline.CmdLineAppBuilder;
import com.github.bogdanovmn.slowloganalyzer.core.LogFile;
import com.github.bogdanovmn.slowloganalyzer.core.LogFileEvents;
import com.github.bogdanovmn.slowloganalyzer.core.TimestampDefinition;
import com.github.bogdanovmn.slowloganalyzer.core.TimestampType;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
	private final static String CMD_OPTION__LOG_NAME   = "log";
	private final static String CMD_OPTION__THRESHOLD  = "threshold";
	private final static String CMD_OPTION__TIMESTAMP_PATTERN = "timestamp-pattern";
	private final static String CMD_OPTION__TIMESTAMP_POSITION = "timestamp-position";
	private final static String CMD_OPTION__TIMESTAMP_TYPE = "timestamp-type";
	private final static String CMD_OPTION__SHOW_SKIPPED = "show-skipped";

	private final static TimestampType DEFAULT_TIMESTAMP_TYPE = TimestampType.TIME;
	private final static int DEFAULT_TIMESTAMP_POSITION = 1;

	public static void main(String[] args) throws Exception {

		new CmdLineAppBuilder(args)
			.withJarName("slow-log-analyzer")
			.withDescription("Shows slow events in a log file")

			.withRequiredArg(CMD_OPTION__LOG_NAME,  "log file name")
			.withRequiredArg(CMD_OPTION__THRESHOLD, "event's duration threshold (in seconds)")

			.withArg(CMD_OPTION__TIMESTAMP_PATTERN, "tp", "log record's timestamp pattern")

			.withArg(
				CMD_OPTION__TIMESTAMP_POSITION, "tpos",
				String.format("timestamp's first token position (default: %d)", DEFAULT_TIMESTAMP_POSITION)
			)

			.withArg(
				CMD_OPTION__TIMESTAMP_TYPE,
				String.format(
					"timestamp's type. Possible values: %s (default: %s)",
						Stream.of(TimestampType.values())
							.map(Enum::name)
							.collect(Collectors.joining(", ")),
						DEFAULT_TIMESTAMP_TYPE
				)
			)
			.withFlag(CMD_OPTION__SHOW_SKIPPED, "show skipped messages")

			.withEntryPoint(
				cmdLine -> {
					TimestampType timestampType = cmdLine.hasOption(CMD_OPTION__TIMESTAMP_TYPE)
						? TimestampType.valueOf(cmdLine.getOptionValue(CMD_OPTION__TIMESTAMP_TYPE))
						: DEFAULT_TIMESTAMP_TYPE;

					DateTimeFormatter pattern = cmdLine.hasOption(CMD_OPTION__TIMESTAMP_PATTERN)
							? DateTimeFormatter.ofPattern(
								cmdLine.getOptionValue(CMD_OPTION__TIMESTAMP_PATTERN)
							)
							: timestampType.defaultPattern();

					LogFileEvents events = new LogFile(
						cmdLine.getOptionValue(CMD_OPTION__LOG_NAME)
					).events(
						TimestampDefinition.builder()
							.type(timestampType)
							.pattern(pattern)
							.firstTokenPosition(
								Integer.parseInt(
									cmdLine.getOptionValue(
										CMD_OPTION__TIMESTAMP_POSITION,
										String.valueOf(DEFAULT_TIMESTAMP_POSITION)
									)
								)
							)
							.build()
					);

					events.withDurationAboveThan(
						Integer.parseInt(
							cmdLine.getOptionValue(CMD_OPTION__THRESHOLD)
						)
					).forEach(System.out::println);

					events.printStatistic(cmdLine.hasOption(CMD_OPTION__SHOW_SKIPPED));

				}
			).build().run();
	}
}