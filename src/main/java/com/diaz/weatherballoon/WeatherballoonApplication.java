package com.diaz.weatherballoon;

import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherballoonApplication implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(WeatherballoonApplication.class);
	private static final String GENERATE_DATA = "generate-data";
	private static final String GENERATE_STATS = "generate-stats";
	private static final String NORMALIZE = "normalize";
	private static final String FILE_NAME_ARG = "file";
	private static final String NORMALIZED_FILE_NAME_ARG = "normalized";
	private static final String DEFAULT_NORMALIZED_FILE_NAME = "normalized.txt";
	private static final String DEFAULT_FILE_NAME = "balloondata.txt";
	private static final String LINES_ARG = "lines";

	@Autowired
	private AppConfiguration configuration;
	@Autowired
	private WeatherObservationFormatter formatter;

	public static void main(String[] args) {
		SpringApplication.run(WeatherballoonApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (args.getSourceArgs().length == 0) {
			logger.info("Application started without arguments. Cannot proceed. Check README.");
			System.exit(-1);
		} 
		
		logger.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));

		String fileName = getArg(args, FILE_NAME_ARG, DEFAULT_FILE_NAME);

		if (args.getNonOptionArgs().contains(GENERATE_DATA)) {
			Long lines = Long.valueOf(getArg(args, LINES_ARG, configuration.getDefaultLines().toString()));
			generateRandomData(fileName, lines);
		} else if (args.getNonOptionArgs().contains(GENERATE_STATS)) {
			generateStats(fileName);
		}  else if (args.getNonOptionArgs().contains(NORMALIZE)) {
			String normalizedFileName = getArg(args, NORMALIZED_FILE_NAME_ARG, DEFAULT_NORMALIZED_FILE_NAME);
			logger.warn("NORMALIZATION Functionality was not implemented");
		}

	}

	private void generateStats(String fileName) throws IOException{
		DataFeed feed = new BalloonDataFeed(fileName);
		Stats stats = new StatsGenerator(configuration.getObservatoriesMap()).generateStats(feed);
		
		logger.warn("External Sort was not implemented yet. Assuming records arrive in order");
		
		System.out.println("Stats: ");
		System.out.println("------ ");
		System.out.println("Min Temp. (celcius): " + stats.getMinTempCelcius());
		System.out.println("Max Temp. (celcius): " + stats.getMaxTempCelcius());
		System.out.println("Mean Temp. (celcius): " + stats.getMeanTempCelcius());
		System.out.println("Distance Travelled (kms): " + stats.getTotalDistanceKms());
		System.out.println("Observation per observatory: " + stats.getObservatoryCounts());
	}

	protected void generateRandomData(String fileName, Long lines) throws IOException {
		logger.info("Will start generating {} into file {}", lines, fileName);
		
		BalloonRandomDataGenerator dataGenerator = new BalloonRandomDataGenerator(configuration.getObservatoriesMap(), formatter, new BalloonDataWriter(fileName));
		dataGenerator.writeRandomData(lines);
		
		logger.info("Finished generating random data");
	}

	protected String getArg(ApplicationArguments args, String argName, String defaultValue) {
		if (args.getOptionValues(argName) != null && !args.getOptionValues(argName).isEmpty()){
			return args.getOptionValues(argName).get(0);
		}
		return defaultValue;
	}
}
