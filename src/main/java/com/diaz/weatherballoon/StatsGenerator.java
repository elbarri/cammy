package com.diaz.weatherballoon;

import static com.diaz.weatherballoon.DistanceScale.KILOMETERS;
import static com.diaz.weatherballoon.TemperatureScale.CELSIUS;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class StatsGenerator {

	private static final int DATE = 0;
	private static final int LOCATION = 1;
	private static final int TEMPERATURE = 2;
	private static final int OBSERVATORY = 3;
	private HashMap<String, Observatory> observatoriesMap;
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	public StatsGenerator(HashMap<String, Observatory> observatoriesMap) {
		super();
		this.observatoriesMap = observatoriesMap;
	}

	public Stats generateStats(DataFeed dataFeed) throws IOException {
		Stats stats = new Stats();
		Location loc = new Location(0, 0);
		long meassureCount = 0, tempAcum = 0;
		HashMap<String, AtomicLong> obsCount = new HashMap<>();
		observatoriesMap.keySet().forEach(o -> obsCount.put(o, new AtomicLong()));
		
		dataFeed.prepareToRead();
		String line = dataFeed.readLine();
		while (line != null) {
			Observation obs = parseLine(line);
			if (parsedLineCorrectly(obs)) {
				Observatory observatory = observatoriesMap.get(obs.getObservatory());
				if (observatory != null) {
					meassureCount++;
					int tempCelcius = getInCelcius(obs.getTemperature(), observatory);
					tempAcum += tempCelcius;
					stats.setMaxTempCelcius(Math.max(stats.getMaxTempCelcius(), tempCelcius));
					stats.setMinTempCelcius(Math.min(stats.getMinTempCelcius(), tempCelcius));
					Location locationKms = getInKms(obs.getLocation(), observatory);
					stats.addDistanceToTotalInKms(loc.calculateDistanceFrom(locationKms));
					obsCount.get(observatory.getName()).incrementAndGet();
					
					loc.setX(locationKms.getX());
					loc.setY(locationKms.getY());
				}
			}
			line = dataFeed.readLine();
		}
		dataFeed.finishedReading();
		
		stats.setObservatoryCounts(obsCount);
		stats.setMeanTempCelcius(meassureCount != 0 ? tempAcum / meassureCount : 0);
		return stats; 
	}

	protected int getInCelcius(int temperature, Observatory observatory) {
		return ( observatory.getTemperature() == CELSIUS) ? temperature : (int)observatory.getTemperature().to(CELSIUS).applyAsDouble(temperature);
	}
	
	protected Location getInKms(Location loc, Observatory observatory) {
		DistanceScale scale = observatory.getDistance();
		int x = (scale == KILOMETERS) ? loc.getX() : (int)KILOMETERS.to(scale).applyAsDouble(loc.getX());
		int y = (scale == KILOMETERS) ? loc.getY() : (int)KILOMETERS.to(scale).applyAsDouble(loc.getY());
		return new Location(x, y);
	}

	protected boolean parsedLineCorrectly(Observation obs) {
		return obs != null;
	}

	/**
	 * Parses a line. NOTE: this logic could be externalized
	 * @param line
	 * @return parsed line. NULL when has invalid data
	 */
	private Observation parseLine(String line) {
		String[] s = line.split("\\|");
		Observation o = new Observation();
		
		try {
			Observatory observatory = observatoriesMap.get(s[OBSERVATORY]);
			o.setObservatory(observatory.getName());
			o.setDate(sdf.parse(s[DATE]).getTime());
			o.setTemperature(Integer.parseInt(s[TEMPERATURE]));
			String[] loc = s[LOCATION].split(",");
			o.setLocation(new Location(Integer.parseInt(loc[0]), Integer.parseInt(loc[1])));
			
		} catch (Exception e) {
			return null;
		}
		
		return o;
	}
	
}
