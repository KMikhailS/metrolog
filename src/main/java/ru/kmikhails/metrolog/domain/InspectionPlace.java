package ru.kmikhails.metrolog.domain;

import java.util.Objects;

public class InspectionPlace {
    private Long id;
    private String inspectionPlace;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInspectionPlace() {
        return inspectionPlace;
    }

    public void setInspectionPlace(String inspectionPlace) {
        this.inspectionPlace = inspectionPlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InspectionPlace that = (InspectionPlace) o;
        return Objects.equals(id, that.id) && Objects.equals(inspectionPlace, that.inspectionPlace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, inspectionPlace);
    }

    @Override
    public String toString() {
        return inspectionPlace;
    }
}
