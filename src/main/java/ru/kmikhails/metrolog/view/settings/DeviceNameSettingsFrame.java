package ru.kmikhails.metrolog.view.settings;

import ru.kmikhails.metrolog.domain.DeviceName;
import ru.kmikhails.metrolog.service.DeviceNameService;

import java.util.List;

public class DeviceNameSettingsFrame extends AbstractDictionarySettingsFrame {

    private final DeviceNameService responsibleService;

    public DeviceNameSettingsFrame(DeviceNameService deviceNameService,
                                   List<String> dictionaryElements, String frameName,
                                   ReconfigureDeviceFrameListener listener) {
        super(dictionaryElements, frameName, listener);
        this.responsibleService = deviceNameService;
    }

    @Override
    protected void updateElement(String oldValue, String newValue) {
        int index = jList.getSelectedIndex();
        DeviceName deviceName = responsibleService.findByName(oldValue);
        deviceName.setDeviceName(newValue);
        responsibleService.update(deviceName);
        listModel.setElementAt(newValue, index);
    }

    @Override
    protected void deleteElement() {
        String element = jList.getSelectedValue();
        if (element != null) {
            DeviceName deviceName = responsibleService.findByName(element);
            responsibleService.deleteById(deviceName.getId());
            listModel.remove(jList.getSelectedIndex());
        }
    }

    @Override
    protected void addElement(String value) {
        DeviceName deviceName = new DeviceName();
        deviceName.setDeviceName(value);
        responsibleService.save(deviceName);
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
