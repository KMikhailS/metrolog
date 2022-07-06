package ru.kmikhails.metrolog.domain;

import java.util.Objects;

public class MeasurementType {
    private Long id;
    private String measurementType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementType that = (MeasurementType) o;
        return Objects.equals(id, that.id) && Objects.equals(measurementType, that.measurementType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, measurementType);
    }

    @Override
    public String toString() {
        return measurementType;
    }
}
