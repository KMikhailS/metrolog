package ru.kmikhails.metrolog.domain;

import java.util.Objects;

public class RegularCondition implements Comparable<RegularCondition> {
    private Long id;
    private String regularCondition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegularCondition() {
        return regularCondition;
    }

    public void setRegularCondition(String regularCondition) {
        this.regularCondition = regularCondition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegularCondition that = (RegularCondition) o;
        return Objects.equals(id, that.id) && Objects.equals(regularCondition, that.regularCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, regularCondition);
    }

    @Override
    public String toString() {
        return regularCondition;
    }

    @Override
    public int compareTo(RegularCondition o) {
        return regularCondition.compareTo(o.getRegularCondition());
    }
}
