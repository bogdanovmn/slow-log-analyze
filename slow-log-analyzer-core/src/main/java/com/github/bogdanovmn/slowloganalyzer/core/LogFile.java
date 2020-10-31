package com.github.bogdanovmn.slowloganalyzer.core;


import java.io.*;

public class LogFile {
	private final File file;

	public LogFile(String fileName) throws FileNotFoundException {
		this.file = new File(fileName);

		if (!this.file.canRead()) {
			throw new FileNotFoundException(
				String.format(
					"Can't read the file: %s", fileName
				)
			);
		}
	}

	public LogFileEvents events(TimestampDefinition timestampDefinition) {
		LogFileEvents events = new LogFileEvents(timestampDefinition);
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				events.add(line);
			}
		}
		catch (IOException e) {
			throw new IllegalStateException("Can't read the file", e);
		}

		return events;
	}
}
