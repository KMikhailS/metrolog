package ru.kmikhails.metrolog.view.settings.productiondate;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import ru.kmikhails.metrolog.domain.Device;
import ru.kmikhails.metrolog.service.DeviceService;
import ru.kmikhails.metrolog.view.DeviceTableModel;
import ru.kmikhails.metrolog.view.util.HorizontalPanel;
import ru.kmikhails.metrolog.view.util.VerticalPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductionDateFrame extends JFrame {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    private final DeviceService deviceService;
    private final DeviceTableModel deviceTableModel;

    private List<Device> devices;
    private List<ProductionDateBox> boxes;

    public ProductionDateFrame(DeviceService deviceService, DeviceTableModel deviceTableModel, List<Device> devices) {
        this.deviceService = deviceService;
        this.deviceTableModel = deviceTableModel;
        this.devices = devices;
    }

    public void init() {
        setTitle("Установка даты изготовления");
        int fontSize = 14;
        Font font = new Font(null, Font.PLAIN, fontSize);

        VerticalPanel mainPanel = new VerticalPanel(10, 0.f);
        VerticalPanel productionDateListPanel = new VerticalPanel(10, 0.f);

        boxes = new ArrayList<>();
        HorizontalPanel labelPanel = getLabelPanel(font);
        productionDateListPanel.add(labelPanel);
        for (Device device : devices) {
            HorizontalPanel productionDatePanel = getProductionDatePanel(device, font, boxes);
            productionDateListPanel.add(productionDatePanel);
        }
        JScrollPane listScroller = new JScrollPane(productionDateListPanel);
        listScroller.setPreferredSize(new Dimension(350, 600));

        Border border = listScroller.getBorder();
        Border margin = new EmptyBorder(10, 10, 10, 10);
        listScroller.setBorder(new CompoundBorder(border, margin));

        HorizontalPanel buttonFrame = getButtonFrame(font);
        mainPanel.add(listScroller);
        mainPanel.add(buttonFrame);

        add(mainPanel);

        setSize(new Dimension(1000, 710));
        setLocationRelativeTo(null);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
//        setResizable(false);
        setVisible(true);
    }

    private HorizontalPanel getLabelPanel(Font font) {
        HorizontalPanel labelPanel = new HorizontalPanel(10, 0.f);
        JTextField deviceNameTextField = new JTextField("      Название");
        deviceNameTextField.setBorder(null);
        deviceNameTextField.setFont(font);
        deviceNameTextField.setColumns(25);
        deviceNameTextField.setEditable(false);
        JTextField factoryNumberTextField = new JTextField("Заводской номер");
        factoryNumberTextField.setBorder(null);
        factoryNumberTextField.setFont(font);
        factoryNumberTextField.setColumns(10);
        factoryNumberTextField.setEditable(false);
        JTextField productionDateTextField = new JTextField("Дата изготовления");
        productionDateTextField.setBorder(null);
        productionDateTextField.setFont(font);
        productionDateTextField.setEditable(false);
        labelPanel.add(deviceNameTextField);
        labelPanel.add(factoryNumberTextField);
        labelPanel.add(productionDateTextField);
        return labelPanel;
    }

    private HorizontalPanel getButtonFrame(Font font) {
        HorizontalPanel buttonFrame = new HorizontalPanel(10, 0.f);
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Сохранить");
        saveButton.setFont(font);
        saveButton.addActionListener(e -> saveProductionDates(boxes));
        JButton cancelButton = new JButton("Отмена");
        cancelButton.setFont(font);
        cancelButton.addActionListener(e -> this.dispose());
        buttonPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonFrame.add(buttonPanel);
        return buttonFrame;
    }

    private void saveProductionDates(List<ProductionDateBox> boxes) {
        for (ProductionDateBox box : boxes) {
            Long deviceId = box.getDeviceId();
            LocalDate productionDate = box.getProductionDateDatePicker().getDate();
            deviceService.updateProductionDate(deviceId, productionDate);
        }
        deviceTableModel.updateTableModel();
    }

    private HorizontalPanel getProductionDatePanel(Device device, Font font, List<ProductionDateBox> boxes) {
        HorizontalPanel productionDatePanel = new HorizontalPanel(10, 0.f);
        JTextField deviceNameTextField = new JTextField(device.getName().toString());
        deviceNameTextField.setFont(font);
        deviceNameTextField.setColumns(25);
        deviceNameTextField.setEditable(false);
        JTextField factoryNumberTextField = new JTextField(device.getFactoryNumber().toString());
        factoryNumberTextField.setFont(font);
        factoryNumberTextField.setColumns(10);
        factoryNumberTextField.setEditable(false);
        DatePicker productionDateDatePicker = new DatePicker();
        DatePickerSettings productionDateSettings = new DatePickerSettings(new Locale("ru"));
        productionDateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        productionDateSettings.setFontMonthAndYearMenuLabels(font);
        productionDateSettings.setFontMonthAndYearNavigationButtons(font);
        productionDateSettings.setFontTodayLabel(font);
        productionDateSettings.setFontClearLabel(font);
        productionDateSettings.setFontCalendarDateLabels(font);
        productionDateSettings.setFontCalendarWeekdayLabels(font);
        productionDateSettings.setFontCalendarWeekNumberLabels(font);
        productionDateSettings.setFontValidDate(font);
        productionDateDatePicker.setSettings(productionDateSettings);
        productionDateDatePicker.setDate(device.getProductionDate());
        productionDatePanel.add(deviceNameTextField);
        productionDatePanel.add(deviceNameTextField);
        productionDatePanel.add(factoryNumberTextField);
        productionDatePanel.add(productionDateDatePicker);

        ProductionDateBox productionDateBox = new ProductionDateBox();
        productionDateBox.setDeviceId(device.getId());
        productionDateBox.setProductionDateDatePicker(productionDateDatePicker);

        boxes.add(productionDateBox);

        return productionDatePanel;
    }
}
