package com.diaz.weatherballoon;

import static com.diaz.weatherballoon.DistanceScale.KILOMETERS;
import static com.diaz.weatherballoon.DistanceScale.METERS;
import static com.diaz.weatherballoon.DistanceScale.MILES;
import static com.diaz.weatherballoon.TemperatureScale.CELSIUS;
import static com.diaz.weatherballoon.TemperatureScale.FAHRENHEIT;
import static com.diaz.weatherballoon.TemperatureScale.KELVIN;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties
@Validated
public class AppConfiguration {
	
	@NotNull
	private List<String> observatories;
	@Valid
	private Temperature temperature = new Temperature();
	@Valid
	private Distance distance = new Distance();
	@NotNull
	private Long defaultLines;
	
	private HashMap<String, Observatory> observatoriesMap = new HashMap<>();
	
	public List<String> getObservatories() {
		return observatories;
	}

	public void setObservatories(List<String> observatories) {
		this.observatories = observatories;
	}

	public Temperature getTemperature() {
		return temperature;
	}

	public void setTemperature(Temperature temperature) {
		this.temperature = temperature;
	}
	
	@PostConstruct
	private void loadObservatoriesMap() {
		observatories.forEach(obs -> {
			observatoriesMap.put(obs, new Observatory(obs, getTemperatureForObservatory(obs), getDistanceForObservatory(obs)));
		});
	}

	TemperatureScale getTemperatureForObservatory(String obs) {
		if (temperature.getCelcius().contains(obs)) {
			return CELSIUS;
		} else if (temperature.getFahrenheit().contains(obs)) {
			return FAHRENHEIT;
		} else if (temperature.getKelvin().contains(obs)) {
			return KELVIN;
		}
		return TemperatureScale.valueOf(temperature.getDefaultTemperature().toUpperCase());
	}

	DistanceScale getDistanceForObservatory(String obs) {
		if (distance.getKilometers().contains(obs)) {
			return KILOMETERS;
		} else if (distance.getMeters().contains(obs)) {
			return METERS;
		} else if (distance.getMiles().contains(obs)) {
			return MILES;
		}
		return DistanceScale.valueOf(distance.getDefaultDistance().toUpperCase());
	}
	
	public static class Temperature {
		private List<String> celcius;
		private List<String> fahrenheit;
		private List<String> kelvin;
		@NotEmpty
		private String defaultTemperature;
		public List<String> getCelcius() {
			return celcius;
		}
		public void setCelcius(List<String> celcius) {
			this.celcius = celcius;
		}
		public List<String> getFahrenheit() {
			return fahrenheit;
		}
		public void setFahrenheit(List<String> fahrenheit) {
			this.fahrenheit = fahrenheit;
		}
		public List<String> getKelvin() {
			return kelvin;
		}
		public void setKelvin(List<String> kelvin) {
			this.kelvin = kelvin;
		}
		public String getDefaultTemperature() {
			return defaultTemperature;
		}
		public void setDefaultTemperature(String defaultTemperature) {
			this.defaultTemperature = defaultTemperature;
		}
	}
	
	public static class Distance {
		private List<String> kilometers;
		private List<String> miles;
		private List<String> meters;
		@NotEmpty
		private String defaultDistance;
		
		public List<String> getKilometers() {
			return kilometers;
		}
		public void setKilometers(List<String> kilometers) {
			this.kilometers = kilometers;
		}
		public List<String> getMiles() {
			return miles;
		}
		public void setMiles(List<String> miles) {
			this.miles = miles;
		}
		public List<String> getMeters() {
			return meters;
		}
		public void setMeters(List<String> meters) {
			this.meters = meters;
		}
		public String getDefaultDistance() {
			return defaultDistance;
		}
		public void setDefaultDistance(String defaultDistance) {
			this.defaultDistance = defaultDistance;
		}
	}

	public Distance getDistance() {
		return distance;
	}

	public void setDistance(Distance distance) {
		this.distance = distance;
	}

	public Long getDefaultLines() {
		return defaultLines;
	}

	public void setDefaultLines(Long defaultLines) {
		this.defaultLines = defaultLines;
	}

	public HashMap<String, Observatory> getObservatoriesMap() {
		return observatoriesMap;
	}

	public void setObservatoriesMap(HashMap<String, Observatory> observatoriesMap) {
		this.observatoriesMap = observatoriesMap;
	}

}
