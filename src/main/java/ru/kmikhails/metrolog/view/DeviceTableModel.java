package ru.kmikhails.metrolog.view;

import ru.kmikhails.metrolog.domain.Device;
import ru.kmikhails.metrolog.service.DeviceService;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DeviceTableModel extends AbstractTableModel {
    private static final String[] TABLE_HEADERS = {
            "Наименование", "Тип", "Номер в реестре", "Пределы(диапазон)", "Класс(разряд)", "Заводской номер"};

    private final String[] columnNames = TABLE_HEADERS;

    private final Class[] columnClass = new Class[]{
            String.class, String.class, String.class, String.class, String.class, String.class};

    private DeviceService deviceService;
    private List<Device> devices;

    public DeviceTableModel(DeviceService deviceService, List<Device> devices) {
        this.deviceService = deviceService;
        this.devices = devices;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    @Override
    public int getRowCount() {
        return devices.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Device device = devices.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return device.getName().toString();
            case 1:
                return device.getType();
            case 2:
                return device.getRegNumber();
            case 3:
                return device.getRange();
            case 4:
                return device.getCategory();
            case 5:
                return device.getFactoryNumber();
            default:
                return null;
        }
    }

    public void updateTable(Device device) {
        deviceService.update(device);
        updateTableModel();
    }

    public void saveDevice(Device device) {
        deviceService.save(device);
        updateTableModel();
    }

    private void updateTableModel() {
        devices = deviceService.findAll();
        this.fireTableDataChanged();
    }

    public void deleteDevice(Long id) {
        deviceService.deleteById(id);
        updateTableModel();
    }
}
