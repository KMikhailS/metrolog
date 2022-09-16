package ru.kmikhails.metrolog.view.settings.dictionaries;

import ru.kmikhails.metrolog.domain.DeviceLocation;
import ru.kmikhails.metrolog.service.DeviceLocationService;

import java.util.List;

public class DeviceLocationSettingsFrame extends AbstractDictionarySettingsFrame {

    private final DeviceLocationService deviceLocationService;

    public DeviceLocationSettingsFrame(DeviceLocationService deviceLocationService,
                                       List<String> dictionaryElements, String frameName,
                                       ReconfigureDeviceFrameListener listener) {
        super(dictionaryElements, frameName, listener);
        this.deviceLocationService = deviceLocationService;
    }

    @Override
    protected void updateElement(String oldValue, String newValue) {
        int index = jList.getSelectedIndex();
        DeviceLocation deviceLocation = deviceLocationService.findByName(oldValue);
        deviceLocation.setDeviceLocation(newValue);
        deviceLocationService.update(deviceLocation);
        listModel.setElementAt(newValue, index);
    }

    @Override
    protected void deleteElement() {
        String element = jList.getSelectedValue();
        if (element != null) {
            DeviceLocation deviceLocation = deviceLocationService.findByName(element);
            deviceLocationService.deleteById(deviceLocation.getId());
            listModel.remove(jList.getSelectedIndex());
        }
    }

    @Override
    protected void addElement(String value) {
        DeviceLocation deviceLocation = new DeviceLocation();
        deviceLocation.setDeviceLocation(value);
        deviceLocationService.save(deviceLocation);
        listModel.addElement(value);
    }

    @Override
    public void saveValue(String value) {
        addElement(value);
    }

    @Override
    public void updateValue(String oldValue, String newValue) {
        updateElement(oldValue, newValue);
    }
}
