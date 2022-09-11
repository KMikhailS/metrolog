package ru.kmikhails.metrolog.view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Locale;

public class ShortDeviceFrame extends JFrame {
    private static final Font FONT = new Font(null, Font.PLAIN, 14);
    private static final Locale LOCALE = new Locale("ru");

    private DatePicker lastInspectionDatePicker;
    private DatePicker nextInspectionDatePicker;
    private JTextField inspectionFrequencyTextField;
    private JComboBox<InspectionPlace> inspectionPlaceComboBox;
    private JTextField inspectionProtocolNumberTextField;
    private JComboBox<InspectionType> inspectionTypeComboBox;
    private JButton saveButton;
    private JButton cancelButton;

    private Long deviceId;

    private final DeviceService deviceService;
    private final DeviceTableModel deviceTableModel;

    private final InspectionPlace[] inspectionPlaces;
    private final InspectionType[] inspectionTypes;

    public ShortDeviceFrame(DeviceService deviceService, DeviceTableModel deviceTableModel,
                            InspectionPlace[] inspectionPlaces, InspectionType[] inspectionTypes) {
        this.deviceTableModel = deviceTableModel;
        this.deviceService = deviceService;
        this.inspectionPlaces = inspectionPlaces;
        this.inspectionTypes = inspectionTypes;
        init();
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public void init() {
        JOptionPane.setDefaultLocale(LOCALE);
        FormLayout layout = new FormLayout(
                "pref,3dlu,pref,10dlu",
                "p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,9dlu,p");

        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();
        CellConstraints cc = new CellConstraints();

        builder.addSeparator("Метрологический контроль", cc.xyw(1, 1, 4));
        builder.addLabel("Дата последней поверки", cc.xy(1, 3));
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
        builder.add(lastInspectionDatePicker, cc.xyw(3, 3, 1));

        builder.addLabel("Периодичность поверки", cc.xy(1, 5));
        inspectionFrequencyTextField = new JTextField();
        inspectionFrequencyTextField.setFont(FONT);
        inspectionFrequencyTextField.addKeyListener(getFrequencyKeyListener());
        inspectionFrequencyTextField.getDocument().addDocumentListener(getFrequencyDocumentListener());
        builder.add(inspectionFrequencyTextField, cc.xyw(3, 5, 1));

        builder.addLabel("Дата следующей поверки", cc.xy(1, 7));
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
        builder.add(nextInspectionDatePicker, cc.xyw(3, 7, 1));

        builder.addLabel("Место проведения поверки", cc.xy(1, 9));
        inspectionPlaceComboBox = new JComboBox<>(inspectionPlaces);
        inspectionPlaceComboBox.setFont(FONT);
        builder.add(inspectionPlaceComboBox, cc.xyw(3, 9, 1));

        builder.addLabel("Номер документа", cc.xy(1, 11));
        inspectionProtocolNumberTextField = new JTextField();
        inspectionProtocolNumberTextField.setFont(FONT);
        builder.add(inspectionProtocolNumberTextField, cc.xyw(3, 11, 1));

        builder.addLabel("Вид МК", cc.xy(1, 13));
        inspectionTypeComboBox = new JComboBox<>(inspectionTypes);
        inspectionTypeComboBox.setFont(FONT);
        builder.add(inspectionTypeComboBox, cc.xyw(3, 13, 1));

        saveButton = new JButton("Сохранить");
        saveButton.setFont(FONT);
        saveButton.addActionListener(e -> saveDevice());

        cancelButton = new JButton("Отмена");
        cancelButton.setFont(FONT);
        cancelButton.addActionListener(e -> this.dispose());
        builder.add(ButtonBarFactory.buildOKCancelBar(saveButton, cancelButton),
                cc.xyw(1, 15, 3));

        this.add(builder.getPanel());
        this.setSize(new Dimension(530, 320));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public void showExistFrame(Device device) {
        setDeviceId(device.getId());
        fillForm(device);
        setVisible(true);
    }

    private void fillForm(Device device) {
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
    }

    private void saveDevice() {
        try {
            Device device = deviceService.findById(deviceId);
            buildDevice(device);
            deviceTableModel.updateTable(device);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void buildDevice(Device device) {
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
