package ru.kmikhails.metrolog.service;

import ru.kmikhails.metrolog.domain.Device;
import ru.kmikhails.metrolog.exception.DeviceException;
import ru.kmikhails.metrolog.repository.DeviceRepository;

import java.util.List;
import java.util.Optional;

public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public void save(Device device) {
        deviceRepository.save(device);
    }

    public void update(Device device) {
        deviceRepository.update(device);
    }

    public void deleteById(Long id) {
        deviceRepository.delete(id);
    }

    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    public Device findById(Long id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceException(String.format("Нет прибора с id [%d]", id)));
    }

    public Device findByNameAndFactoryNumber(String name, String factoryNumber) {
        return deviceRepository.findByNameAndFactoryNumber(name, factoryNumber)
                .orElse(null);
    }
}
