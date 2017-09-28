package com.diaz.weatherballoon;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

/**
 * Stolen from stackoverflow ^^
 */
public enum TemperatureScale {
    CELSIUS, FAHRENHEIT, KELVIN;
    private final Map<TemperatureScale, DoubleUnaryOperator> ops = new HashMap<>();

    public DoubleUnaryOperator to(TemperatureScale to) {
        return to == this ? DoubleUnaryOperator.identity() : ops.get(to);
    }

    static {
        put(    CELSIUS, FAHRENHEIT, c -> c * 9.0 / 5.0 + 32.0     );
        put(    CELSIUS,     KELVIN, c -> c + 273.15               );
        put( FAHRENHEIT,    CELSIUS, f -> (f - 32.0) * 5.0 / 9.0   );
        put( FAHRENHEIT,     KELVIN, f -> (f + 459.67) * 5.0 / 9.0 );
        put(     KELVIN, FAHRENHEIT, k -> k * 9.0 / 5.0 + 459.67   );
        put(     KELVIN,    CELSIUS, k -> k - 273.15               );
    }

    private static void put(TemperatureScale from, TemperatureScale to, DoubleUnaryOperator op) {
        from.ops.put(to, op);
    }
}
