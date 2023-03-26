package ru.kmikhails.metrolog.view;

import ru.kmikhails.metrolog.domain.Device;
import ru.kmikhails.metrolog.service.DeviceService;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeviceTableModel extends AbstractTableModel {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    private static final String CHECK_STATE = "в поверке";
    private static final String STORING_STATE = "дл. хранение";
    private static final String DEFECT_STATE = "брак";
    private static final List<String> NON_HIGHLIGHT_STATE = Arrays.asList("дл. хранение", "брак");
    private static final String[] TABLE_HEADERS = {
            "Наименование", "Тип", "Номер в реестре", "Пределы(диапазон)", "Класс(разряд)", "Заводской номер", "Дата поверки"};

    private final String[] columnNames = TABLE_HEADERS;

    private final Class[] columnClass = new Class[]{
            String.class, String.class, String.class, String.class, String.class, String.class, String.class};

    private final DeviceService deviceService;
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
            case 6:
                if (CHECK_STATE.equals(device.getRegularCondition().getRegularCondition())) {
                    return CHECK_STATE;
                } else if (STORING_STATE.equals(device.getRegularCondition().getRegularCondition())) {
                    return STORING_STATE;
                } else if (DEFECT_STATE.equals(device.getRegularCondition().getRegularCondition())) {
                    return DEFECT_STATE;
                }
                return FORMATTER.format(device.getNextInspectionDate());
            default:
                return null;
        }
    }

    public Color getRowColor(int row) {
        Device device = devices.get(row);
        LocalDate now = LocalDate.now();
        LocalDate nextInspectionDate = device.getNextInspectionDate();
        long period = ChronoUnit.DAYS.between(now, nextInspectionDate);
        if (device.getRegularCondition() != null
                && !NON_HIGHLIGHT_STATE.contains(device.getRegularCondition().getRegularCondition())) {
            if (CHECK_STATE.equals(device.getRegularCondition().getRegularCondition())) {
                return Color.GREEN;
            }
            if (period < 2) {
                return Color.RED;
            }
            if (period < 14) {
                return Color.YELLOW;
            }
        }
        return Color.WHITE;
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
