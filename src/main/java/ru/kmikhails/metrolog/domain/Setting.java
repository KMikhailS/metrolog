package ru.kmikhails.metrolog.domain;

import java.util.Objects;

public class Setting {
    private Long id;
    private String type;
    private String description;
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Setting setting = (Setting) o;
        return Objects.equals(type, setting.type)
                && Objects.equals(description, setting.description)
                && Objects.equals(value, setting.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, description, value);
    }

    @Override
    public String toString() {
        return "Setting{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
