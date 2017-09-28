package com.diaz.weatherballoon;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Component;

/**
 * Formats data adding random noise. 
 * NOTE: The randomness can be improved.
 */
@Component
public class NoisyObservationsFormatter implements WeatherObservationFormatter {
	/* These set of values could be mapped onto properites file */
	private static final double INVALID_PERCENTAGE = 0.3;
	private static final char SEPARATOR = '|';
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	public NoisyObservationsFormatter() {
		super();
	}
    
	@Override
	public String formatObservation(Observation obs) {
		double random = Math.random();
		String formattedLine = buildNormalLine(obs);
		
		if (random>INVALID_PERCENTAGE) {
			return formattedLine;
		}
		if (random < 0.05) {
			return mixDates(obs.getDate(), formattedLine); 
		} else if (random < 0.1) {
			return breakDate(obs.getDate(), formattedLine);
		} else if (random < 0.15) {
			return breakObservatory(obs.getObservatory(), formattedLine);
		} else if (random < 0.2) {
			return breakDistance(obs.getLocation(), formattedLine);
		} else if (random < 0.25) {
			return breakTemperature(obs.getTemperature(), formattedLine);
		}
		return "this-is-a|corrupted-line\n";
	}

	protected String breakDistance(Location loc, String formattedLine) {
		return formattedLine.replace(loc.getX() + "," + loc.getY(), "555");
	}
	
	protected String breakTemperature(int temp, String formattedLine) {
		return formattedLine.replace("," + temp, "RRRR");
	}
	
	protected String breakObservatory(String observatory, String formattedLine) {
		return formattedLine.replace(observatory, "POFPMFLF");
	}

	protected String breakDate(long date, String formattedLine) {
		return formattedLine.replace(sdf.format(new Date(date)), "2014-13-31T13:44");
	}

	protected String mixDates(long date, String formattedLine) {
		int randomDelay = new Random().nextInt(Integer.MAX_VALUE);
		return formattedLine.replace(sdf.format(new Date(date)), sdf.format(new Date(date-randomDelay)));
	}

	protected String buildNormalLine(Observation obs) {
		StringBuilder sb = new StringBuilder();
		sb.append(sdf.format(new Date(obs.getDate()))).append(SEPARATOR);
		sb.append(obs.getLocation().getX()).append(',');
		sb.append(obs.getLocation().getY()).append(SEPARATOR);
		sb.append(obs.getTemperature()).append(SEPARATOR);
		sb.append(obs.getObservatory());
		sb.append('\n');
		return sb.toString();
	}

}
