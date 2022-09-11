package ru.kmikhails.metrolog.view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import ru.kmikhails.metrolog.domain.*;
import ru.kmikhails.metrolog.exception.DeviceException;
import ru.kmikhails.metrolog.service.DeviceService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Locale;

public class DeviceFrame extends JFrame {
    private static final Font FONT = new Font(null, Font.PLAIN, 14);
    private static final Locale LOCALE = new Locale("ru");

    private JComboBox<DeviceName> deviceNameJComboBox;
    private JTextField typeTextField;
    private JTextField regNumberTextField;
    private JTextField rangeTextField;
    private JTextField categoryTextField;
    private JTextField factoryNumberTextField;
    private DatePicker lastInspectionDatePicker;
    private DatePicker nextInspectionDatePicker;
    private JTextField inspectionFrequencyTextField;
    private JComboBox<InspectionPlace> inspectionPlaceComboBox;
    private JTextField inspectionProtocolNumberTextField;
    private JComboBox<InspectionType> inspectionTypeComboBox;
    private JTextField responsibleTextField;
    private JComboBox<DeviceLocation> deviceLocationTypeComboBox;
    private JComboBox<RegularCondition> regularConditionTypeComboBox;
    private JComboBox<MeasurementType> measurementTypeComboBox;
    private JButton saveButton;
    private JButton cancelButton;

    private boolean isUpdate;
    private Long deviceId;

    private final DeviceService deviceService;
    private final DeviceTableModel deviceTableModel;

    private final DeviceLocation[] deviceLocations;
    private final InspectionPlace[] inspectionPlaces;
    private final InspectionType[] inspectionTypes;
    private final MeasurementType[] measurementTypes;
    private final RegularCondition[] regularConditions;
    private final DeviceName[] deviceNames;

    public DeviceFrame(DeviceService deviceService, DeviceTableModel deviceTableModel,
                       DeviceLocation[] deviceLocations, InspectionPlace[] inspectionPlaces,
                       InspectionType[] inspectionTypes, MeasurementType[] measurementTypes,
                       RegularCondition[] regularConditions, DeviceName[] deviceNames) {
        this.deviceTableModel = deviceTableModel;
        this.deviceService = deviceService;
        this.deviceLocations = deviceLocations;
        this.inspectionPlaces = inspectionPlaces;
        this.inspectionTypes = inspectionTypes;
        this.measurementTypes = measurementTypes;
        this.regularConditions = regularConditions;
        this.deviceNames = deviceNames;
        init();
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public void init() {
        FormLayout layout = new FormLayout(
                "pref,3dlu,pref,10dlu",
                "p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p," +
                        "9dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p," +
                        "9dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,9dlu,p");


        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();


        CellConstraints cc = new CellConstraints();


        builder.addSeparator("Данные о СИ", cc.xyw(1, 1, 4));
        builder.addLabel("Наименование СИ", cc.xy(1, 3));
        deviceNameJComboBox = new JComboBox<>(deviceNames);
        deviceNameJComboBox.setFont(FONT);
        builder.add(deviceNameJComboBox, cc.xyw(3, 3, 1));

        builder.addLabel("Тип СИ", cc.xy(1, 5));
        typeTextField = new JTextField();
        typeTextField.setFont(FONT);
        typeTextField.setColumns(25);
        builder.add(typeTextField, cc.xyw(3, 5, 1));

        builder.addLabel("Номер в реестре", cc.xy(1, 7));
        regNumberTextField = new JTextField();
        regNumberTextField.setFont(FONT);
        builder.add(regNumberTextField, cc.xyw(3, 7, 1));

        builder.addLabel("Пределы(диапазон)", cc.xy(1, 9));
        rangeTextField = new JTextField();
        rangeTextField.setFont(FONT);
        builder.add(rangeTextField, cc.xyw(3, 9, 1));

        builder.addLabel("Класс(разряд)", cc.xy(1, 11));
        categoryTextField = new JTextField();
        categoryTextField.setFont(FONT);
        builder.add(categoryTextField, cc.xyw(3, 11, 1));

        builder.addLabel("Заводской №", cc.xy(1, 13));
        factoryNumberTextField = new JTextField();
        factoryNumberTextField.setFont(FONT);
        builder.add(factoryNumberTextField, cc.xyw(3, 13, 1));

        builder.addSeparator("Метрологический контроль", cc.xyw(1, 15, 4));
        builder.addLabel("Дата последней поверки", cc.xy(1, 17));
        lastInspectionDatePicker = new DatePicker();
        DatePickerSettings lastDateSettings = new DatePickerSettings(LOCALE);
        lastDateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        lastDateSettings.setFontMonthAndYearMenuLabels(FONT);
        lastDateSettings.setFontMonthAndYearNavigationButtons(FONT);
        lastDateSettings.setFontTodayLabel(FONT);
        lastDateSettings.setFontClearLabel(FONT);
        lastDateSettings.setFontCalendarDateLabels(FONT);
        lastDateSettings.setFontCalendarWeekdayLabels(FONT);
        lastDateSettings.setFontCalendarWeekNumberLabels(FONT);
        lastDateSettings.setFontValidDate(FONT);
        lastInspectionDatePicker = new DatePicker(lastDateSettings);
        lastInspectionDatePicker.addDateChangeListener(getLastInspectionDateListener());
        builder.add(lastInspectionDatePicker, cc.xyw(3, 17, 1));

        builder.addLabel("Периодичность поверки", cc.xy(1, 19));
        inspectionFrequencyTextField = new JTextField();
        inspectionFrequencyTextField.setFont(FONT);
        inspectionFrequencyTextField.addKeyListener(getFrequencyKeyListener());
        inspectionFrequencyTextField.getDocument().addDocumentListener(getFrequencyDocumentListener());
        builder.add(inspectionFrequencyTextField, cc.xyw(3, 19, 1));

        builder.addLabel("Дата следующей поверки", cc.xy(1, 21));
        nextInspectionDatePicker = new DatePicker();
        DatePickerSettings nextDateSettings = new DatePickerSettings(LOCALE);
        nextDateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        nextDateSettings.setFontMonthAndYearMenuLabels(FONT);
        nextDateSettings.setFontMonthAndYearNavigationButtons(FONT);
        nextDateSettings.setFontTodayLabel(FONT);
        nextDateSettings.setFontClearLabel(FONT);
        nextDateSettings.setFontCalendarDateLabels(FONT);
        nextDateSettings.setFontCalendarWeekdayLabels(FONT);
        nextDateSettings.setFontCalendarWeekNumberLabels(FONT);
        nextDateSettings.setFontValidDate(FONT);
        nextInspectionDatePicker = new DatePicker(nextDateSettings);
        builder.add(nextInspectionDatePicker, cc.xyw(3, 21, 1));

        builder.addLabel("Место проведения поверки", cc.xy(1, 23));
        inspectionPlaceComboBox = new JComboBox<>(inspectionPlaces);
        inspectionPlaceComboBox.setFont(FONT);
        builder.add(inspectionPlaceComboBox, cc.xyw(3, 23, 1));

        builder.addLabel("Номер документа", cc.xy(1, 25));
        inspectionProtocolNumberTextField = new JTextField();
        inspectionProtocolNumberTextField.setFont(FONT);
        builder.add(inspectionProtocolNumberTextField, cc.xyw(3, 25, 1));

        builder.addLabel("Вид МК", cc.xy(1, 27));
        inspectionTypeComboBox = new JComboBox<>(inspectionTypes);
        inspectionTypeComboBox.setFont(FONT);
        builder.add(inspectionTypeComboBox, cc.xyw(3, 27, 1));

        builder.addSeparator("Дополнительные данные", cc.xyw(1, 29, 4));
        builder.addLabel("Ответственный", cc.xy(1, 31));
        responsibleTextField = new JTextField();
        responsibleTextField.setFont(FONT);
        builder.add(responsibleTextField, cc.xyw(3, 31, 1));

        builder.addLabel("Место установки", cc.xy(1, 33));
        deviceLocationTypeComboBox = new JComboBox<>(deviceLocations);
        deviceLocationTypeComboBox.setFont(FONT);
        builder.add(deviceLocationTypeComboBox, cc.xyw(3, 33, 1));

        builder.addLabel("Штатное состояние", cc.xy(1, 35));
        regularConditionTypeComboBox = new JComboBox<>(regularConditions);
        regularConditionTypeComboBox.setFont(FONT);
        builder.add(regularConditionTypeComboBox, cc.xyw(3, 35, 1));

        builder.addLabel("Вид измерений", cc.xy(1, 37));
        measurementTypeComboBox = new JComboBox<>(measurementTypes);
        measurementTypeComboBox.setFont(FONT);
        builder.add(measurementTypeComboBox, cc.xyw(3, 37, 1));

        saveButton = new JButton("Сохранить");
        saveButton.setFont(FONT);
        saveButton.addActionListener(e -> saveDevice());
//        builder.add(okButton, cc.xyw(3, 35, 1));

        cancelButton = new JButton("Отмена");
        cancelButton.setFont(FONT);
        cancelButton.addActionListener(e -> this.dispose());
//        builder.add(cancelButton, cc.xyw(5, 35, 1));
        builder.add(ButtonBarFactory.buildOKCancelBar(saveButton, cancelButton),
                cc.xyw(1, 39, 3));

        this.add(builder.getPanel());
        this.setSize(new Dimension(700, 690));
//        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//        this.setVisible(true);
    }

    public void showNewFrame() {
        setDeviceId(null);
        deviceNameJComboBox.setSelectedIndex(-1);
        typeTextField.setText("");
        rangeTextField.setText("");
        regNumberTextField.setText("");
        categoryTextField.setText("");
        factoryNumberTextField.setText("");
        lastInspectionDatePicker.setText("");
        nextInspectionDatePicker.setText("");
        inspectionFrequencyTextField.setText("");
        inspectionProtocolNumberTextField.setText("");
        deviceLocationTypeComboBox.setSelectedIndex(-1);
        inspectionTypeComboBox.setSelectedIndex(-1);
        inspectionPlaceComboBox.setSelectedIndex(-1);
        measurementTypeComboBox.setSelectedIndex(-1);
        regularConditionTypeComboBox.setSelectedIndex(-1);
        responsibleTextField.setText("");
        setVisible(true);
    }

    public void showExistFrame(Device device) {
        setDeviceId(device.getId());
        fillForm(device);
        setVisible(true);
    }


    public void showCopyFrame(Device device) {
        setDeviceId(null);
        fillForm(device);
        setVisible(true);
    }

    private void fillForm(Device device) {
        DeviceName deviceName = new DeviceName();
        deviceName.setId(device.getName().getId());
        deviceName.setDeviceName(device.getName().getDeviceName());
        deviceNameJComboBox.setSelectedItem(deviceName);
        typeTextField.setText(device.getType());
        regNumberTextField.setText(device.getRegNumber());
        rangeTextField.setText(device.getRange());
        categoryTextField.setText(device.getCategory());
        factoryNumberTextField.setText(device.getFactoryNumber());
        lastInspectionDatePicker.setDate(device.getLastInspectionDate());
        nextInspectionDatePicker.setDate(device.getNextInspectionDate());
        inspectionFrequencyTextField.setText(String.valueOf(device.getInspectionFrequency()));
        inspectionProtocolNumberTextField.setText(device.getInspectionProtocolNumber());
        InspectionPlace inspectionPlace = new InspectionPlace();
        inspectionPlace.setId(device.getInspectionPlace().getId());
        inspectionPlace.setInspectionPlace(device.getInspectionPlace().getInspectionPlace());
        inspectionPlaceComboBox.setSelectedItem(inspectionPlace);
        InspectionType inspectionType = new InspectionType();
        inspectionType.setId(device.getInspectionType().getId());
        inspectionType.setInspectionType(device.getInspectionType().getInspectionType());
        inspectionTypeComboBox.setSelectedItem(inspectionType);
        DeviceLocation deviceLocation = new DeviceLocation();
        deviceLocation.setId(device.getDeviceLocation().getId());
        deviceLocation.setDeviceLocation(device.getDeviceLocation().getDeviceLocation());
        deviceLocationTypeComboBox.setSelectedItem(deviceLocation);
        RegularCondition regularCondition = new RegularCondition();
        regularCondition.setId(device.getRegularCondition().getId());
        regularCondition.setRegularCondition(device.getRegularCondition().getRegularCondition());
        regularConditionTypeComboBox.setSelectedItem(regularCondition);
        MeasurementType measurementType = new MeasurementType();
        measurementType.setId(device.getMeasurementType().getId());
        measurementType.setMeasurementType(device.getMeasurementType().getMeasurementType());
        measurementTypeComboBox.setSelectedItem(measurementType);
        responsibleTextField.setText(device.getResponsible());
    }

    private void saveDevice() {
        try {
            Device device;
            boolean valid = validateForm();
            if (valid) {
                if (deviceId != null) {
//                Device existDevice = deviceService.findById(deviceId);
                    device = buildDevice(deviceId);
                    deviceTableModel.updateTable(device);
                } else {
                    String deviceName = ((DeviceName) deviceNameJComboBox.getSelectedItem()).getDeviceName();
                    if (deviceService.findByNameAndFactoryNumber(deviceName, factoryNumberTextField.getText()) != null) {
                        throw new DeviceException("Прибор с такими наименованием и заводским номером уже существует");
                    }
                    device = buildDevice(null);
                    deviceTableModel.saveDevice(device);
                }
                this.dispose();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateForm() {
        if (deviceNameJComboBox.getSelectedItem() == null) {
            throw new DeviceException("Наименование СИ не должно быть пустым");
        }
        if (factoryNumberTextField == null || factoryNumberTextField.getText().isEmpty()) {
            throw new DeviceException("Заводской номер не должен быть пустым");
        }
        if (inspectionPlaceComboBox.getSelectedItem() == null
                || inspectionTypeComboBox.getSelectedItem() == null
                || deviceLocationTypeComboBox.getSelectedItem() == null
                || measurementTypeComboBox.getSelectedItem() == null
                || responsibleTextField == null
                || factoryNumberTextField.getText().isEmpty()) {
            int option = JOptionPane.showConfirmDialog(this, "Заполнены не все необходимые поля!\nВсё равно сохранить?",
                    "Сохранение", JOptionPane.YES_NO_OPTION);
            if (option == 1) {
                return false;
            }
        }

        if (inspectionFrequencyTextField.getText() != null && !inspectionFrequencyTextField.getText().isEmpty()) {
            try {
                int frequency = Integer.parseInt(inspectionFrequencyTextField.getText());
                if (frequency < 0) {
                    throw new DeviceException("Периодичность поверки меньше нуля");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Периодичность поверки должна быть неотрицательным числом", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
        return true;
    }

    private Device buildDevice(Long id) {
        Device device = new Device();
        device.setId(id);
        DeviceName deviceName = new DeviceName();
        deviceName.setId(((DeviceName)deviceNameJComboBox.getSelectedItem()).getId());
        deviceName.setDeviceName(((DeviceName) deviceNameJComboBox.getSelectedItem()).getDeviceName());
        device.setName(deviceName);
        device.setType(typeTextField.getText());
        device.setRegNumber(regNumberTextField.getText());
        device.setRange(rangeTextField.getText());
        device.setCategory(categoryTextField.getText());
        device.setFactoryNumber(factoryNumberTextField.getText());
        device.setLastInspectionDate(lastInspectionDatePicker.getDate());
        device.setNextInspectionDate(nextInspectionDatePicker.getDate());
        if (inspectionFrequencyTextField.getText() != null && !inspectionFrequencyTextField.getText().isEmpty()) {
            device.setInspectionFrequency(Integer.parseInt(inspectionFrequencyTextField.getText()));
        }
        device.setInspectionProtocolNumber(inspectionProtocolNumberTextField.getText());

        if (inspectionPlaceComboBox.getSelectedItem() != null) {
            InspectionPlace inspectionPlace = new InspectionPlace();
            inspectionPlace.setId(((InspectionPlace) inspectionPlaceComboBox.getSelectedItem()).getId());
            inspectionPlace.setInspectionPlace(((InspectionPlace) inspectionPlaceComboBox.getSelectedItem()).getInspectionPlace());
            device.setInspectionPlace(inspectionPlace);
        }

        if (inspectionTypeComboBox.getSelectedItem() != null) {
            InspectionType inspectionType = new InspectionType();
            inspectionType.setId(((InspectionType) inspectionTypeComboBox.getSelectedItem()).getId());
            inspectionType.setInspectionType(((InspectionType) inspectionTypeComboBox.getSelectedItem()).getInspectionType());
            device.setInspectionType(inspectionType);
        }

        if (deviceLocationTypeComboBox.getSelectedItem() != null) {
            DeviceLocation deviceLocation = new DeviceLocation();
            deviceLocation.setId(((DeviceLocation) deviceLocationTypeComboBox.getSelectedItem()).getId());
            deviceLocation.setDeviceLocation(((DeviceLocation) deviceLocationTypeComboBox.getSelectedItem()).getDeviceLocation());
            device.setDeviceLocation(deviceLocation);
        }

        if (regularConditionTypeComboBox.getSelectedItem() != null) {
            RegularCondition regularCondition = new RegularCondition();
            regularCondition.setId(((RegularCondition) regularConditionTypeComboBox.getSelectedItem()).getId());
            regularCondition.setRegularCondition(((RegularCondition) regularConditionTypeComboBox.getSelectedItem()).getRegularCondition());
            device.setRegularCondition(regularCondition);
        }

        if (measurementTypeComboBox.getSelectedItem() != null) {
            MeasurementType measurementType = new MeasurementType();
            measurementType.setId(((MeasurementType) measurementTypeComboBox.getSelectedItem()).getId());
            measurementType.setMeasurementType(((MeasurementType) measurementTypeComboBox.getSelectedItem()).getMeasurementType());
            device.setMeasurementType(measurementType);
        }

        device.setResponsible(responsibleTextField.getText());
//        device.sethistory;

        return device;
    }

    private KeyListener getFrequencyKeyListener() {
        return new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char symbol = e.getKeyChar();
                if (Character.isAlphabetic(symbol) || (symbol == KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        };
    }

    private DocumentListener getFrequencyDocumentListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                int frequency = Integer.parseInt(inspectionFrequencyTextField.getText());
                if (frequency > 0) {
                    LocalDate lastDate = lastInspectionDatePicker.getDate();
                    if (lastDate != null) {
                        nextInspectionDatePicker.setDate(lastDate.plusMonths(frequency));
                    }
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        };
    }

    private DateChangeListener getLastInspectionDateListener() {
        return dateChangeEvent -> {
            if (inspectionFrequencyTextField.getText() != null
                    && !inspectionFrequencyTextField.getText().isEmpty()
                    && dateChangeEvent.getNewDate() != null) {
                LocalDate localDate = dateChangeEvent.getNewDate().plusMonths(Long.parseLong(inspectionFrequencyTextField.getText()));
                nextInspectionDatePicker.setDate(localDate);
            }
        };
    }
}
