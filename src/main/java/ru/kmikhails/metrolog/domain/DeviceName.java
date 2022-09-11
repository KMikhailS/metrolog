package ru.kmikhails.metrolog.domain;

import java.util.Objects;

public class DeviceName implements Comparable<DeviceName> {
    private Long id;
    private String deviceName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceName that = (DeviceName) o;
        return Objects.equals(id, that.id) && Objects.equals(deviceName, that.deviceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deviceName);
    }

    @Override
    public String toString() {
        return deviceName;
    }

    @Override
    public int compareTo(DeviceName o) {
        return deviceName.compareTo(o.getDeviceName());
    }
}
