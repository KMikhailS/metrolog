package ru.kmikhails.metrolog.view.util;

import ru.kmikhails.metrolog.domain.Device;
import ru.kmikhails.metrolog.service.DeviceService;

import java.time.LocalDate;
import java.util.List;

public class CustomAction {

    private final DeviceService deviceService;

    public CustomAction(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public void processCustomAction() {
        deleteOneDayFromNextInspectionDate();
    }

    private void deleteOneDayFromNextInspectionDate() {
        List<Device> devices = deviceService.findAll();
        for (Device device : devices) {
            LocalDate newDate = device.getNextInspectionDate().minusDays(1);
            device.setNextInspectionDate(newDate);
            deviceService.update(device);
        }
    }
}
