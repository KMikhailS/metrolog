package ru.kmikhails.metrolog.service;

import ru.kmikhails.metrolog.domain.DeviceLocation;
import ru.kmikhails.metrolog.repository.DeviceLocationRepository;

public class DeviceLocationService extends AbstractService<DeviceLocation>{

    private final DeviceLocationRepository deviceLocationRepository;

    public DeviceLocationService(DeviceLocationRepository deviceLocationRepository) {
        super(deviceLocationRepository);
        this.deviceLocationRepository = deviceLocationRepository;
    }

    public void update(DeviceLocation deviceLocation) {
        deviceLocationRepository.update(deviceLocation);
    }
}
