package com.diaz.weatherballoon;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;


public enum DistanceScale {
    KILOMETERS, MILES, METERS;
    private final Map<DistanceScale, DoubleUnaryOperator> ops = new HashMap<>();

    public DoubleUnaryOperator to(DistanceScale to) {
    	return to == this ? DoubleUnaryOperator.identity() : ops.get(to);
    }

    static {
        put(KILOMETERS, MILES, km -> km * 0.621371);
        put(KILOMETERS, METERS, km -> km * 1000);
        put(MILES, KILOMETERS, mi -> mi * 0.621371);
        put(MILES, METERS, mi -> mi * 1609.34);
        put(METERS, KILOMETERS, me -> me / 1000);
        put(METERS,	MILES, me -> me / 1609.34);
    }

    private static void put(DistanceScale from, DistanceScale to, DoubleUnaryOperator op) {
        from.ops.put(to, op);
    }
}
