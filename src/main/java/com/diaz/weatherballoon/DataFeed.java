package com.diaz.weatherballoon;

import java.io.IOException;

public interface DataFeed {
	void prepareToRead() throws IOException;

	String readLine() throws IOException;

	void finishedReading() throws IOException;
}
