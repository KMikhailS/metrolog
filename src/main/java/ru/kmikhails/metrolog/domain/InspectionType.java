package ru.kmikhails.metrolog.domain;

import java.util.Objects;

public class InspectionType {
    private Long id;
    private String inspectionType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(String inspectionType) {
        this.inspectionType = inspectionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InspectionType that = (InspectionType) o;
        return Objects.equals(id, that.id) && Objects.equals(inspectionType, that.inspectionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, inspectionType);
    }

    @Override
    public String toString() {
        return inspectionType;
    }
}
