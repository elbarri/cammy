package com.diaz.weatherballoon;

import static com.diaz.weatherballoon.DistanceScale.KILOMETERS;
import static com.diaz.weatherballoon.TemperatureScale.CELSIUS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Creates random data 
 * NOTE: The randomness can be improved.
 */
public class BalloonRandomDataGenerator 
{
	/* These set of values could be mapped onto properites file */
	private static final long TIME_INCREMENTS = 1000L;
	private static final int BOUND_CELCIUS = 70;
	private static final int BOUND_DISTANCE_KM = 20000;
	
	private final Map<String, Observatory> observatoriesMap;
	private final List<String> observatoriesList = new ArrayList<>();
	private final MassiveDataWriter dataWriter;
	private final WeatherObservationFormatter formatter;
	
    public BalloonRandomDataGenerator(final Map<String, Observatory> observatoriesMap, WeatherObservationFormatter formatter, 
    		MassiveDataWriter dataWritter) {
    	this.observatoriesMap = observatoriesMap;
    	this.observatoriesList.addAll(observatoriesMap.keySet());
    	this.formatter = formatter;
    	this.dataWriter = dataWritter; 
    }
    
    public void writeRandomData(long lines) throws IOException{
    	dataWriter.prepareToWrite();

    	long incrementalDate = getInitialDateInMillis();
    	
    	for (int i = 0; i < lines; i++) {
    		incrementalDate += TIME_INCREMENTS;
    		Observation obs = createObservation(incrementalDate);
    		dataWriter.writeLine(formatter.formatObservation(obs));
		}
    	
    	dataWriter.finishedWriting();
    	
    }

	protected Observation createObservation(long incrementalDate) {
		Observatory randomObservatory = getRandomObservatory();
		Location loc = new Location(getRandomDistanceConvertedTo(randomObservatory.getDistance()), 
				getRandomDistanceConvertedTo(randomObservatory.getDistance()));
		Observation obs = new Observation(randomObservatory.getName(), incrementalDate, 
				loc, getRandomTempConvertedTo(randomObservatory.getTemperature()));
		return obs;
	}
    
	private int getRandomDistanceConvertedTo(DistanceScale scale) {
		Random random = new Random();
        int distance = random.nextInt(BOUND_DISTANCE_KM);
        
        return (scale == KILOMETERS) ? distance : (int)KILOMETERS.to(scale).applyAsDouble(distance);
	}

	private long getInitialDateInMillis() {
		Calendar calendar = Calendar.getInstance();
    	/*value could be obtained from properites file*/
    	calendar.set(1990, 1, 1);
		return calendar.getTimeInMillis();
	}

    private int getRandomTempConvertedTo(TemperatureScale scale) {
    	Random random = new Random();
        int temp = random.nextInt(BOUND_CELCIUS) * (Math.random()>0.2 ? -1 : 1);
        
        return (scale == CELSIUS) ? temp : (int)CELSIUS.to(scale).applyAsDouble(temp);
	}

	private Observatory getRandomObservatory() {
    	return observatoriesMap.get(observatoriesList.get(new Random().nextInt(observatoriesList.size())));
    }
    
}
