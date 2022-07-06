package ru.kmikhails.metrolog.service;

import ru.kmikhails.metrolog.domain.DeviceName;
import ru.kmikhails.metrolog.repository.DeviceNameRepository;

public class DeviceNameService extends AbstractService<DeviceName> {

    private final DeviceNameRepository deviceNameRepository;

    public DeviceNameService(DeviceNameRepository deviceNameRepository) {
        super(deviceNameRepository);
        this.deviceNameRepository = deviceNameRepository;
    }

    public void update(DeviceName deviceName) {
        deviceNameRepository.update(deviceName);
    }
}
