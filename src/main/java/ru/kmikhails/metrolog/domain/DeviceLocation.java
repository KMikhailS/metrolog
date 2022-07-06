package ru.kmikhails.metrolog.domain;

import java.util.Objects;

public class DeviceLocation {
    private Long id;
    private String deviceLocation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceLocation() {
        return deviceLocation;
    }

    public void setDeviceLocation(String deviceLocation) {
        this.deviceLocation = deviceLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceLocation that = (DeviceLocation) o;
        return Objects.equals(id, that.id) && Objects.equals(deviceLocation, that.deviceLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deviceLocation);
    }

    @Override
    public String toString() {
        return deviceLocation;
    }
}
