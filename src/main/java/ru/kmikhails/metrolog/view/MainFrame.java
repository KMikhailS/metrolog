package ru.kmikhails.metrolog.view;

import ru.kmikhails.metrolog.domain.*;
import ru.kmikhails.metrolog.service.*;
import ru.kmikhails.metrolog.view.settings.*;
import ru.kmikhails.metrolog.view.util.TableMouseListener;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class MainFrame extends JFrame implements ReconfigureDeviceFrameListener {
    private static final String MENU = "Меню";
    private static final String ADD = "Добавить";
    private static final String UPDATE = "Обновить";
    private static final String COPY = "Копировать";
    private static final String DELETE = "Удалить";
    private static final String SETTINGS = "Настройки";
    private static final String INSPECTION_PLACE = "Место проведения поверки";
    private static final String INSPECTION_TYPE = "Тип МК";
    private static final String MEASUREMENT_TYPE = "Тип измерения";
    private static final String REGULAR_CONDITION = "Штатное состояние";
    private static final String DEVICE_NAME = "Наименование СИ";
    private static final String DEVICE_LOCATION = "Место установки";

    private DeviceTableModel deviceTableModel;
    private JTable table;
    private JScrollPane deviceScrollPane;
    private DeviceFrame deviceFrame;
    private Font font;

    private DeviceLocation[] deviceLocations;
    private InspectionPlace[] inspectionPlaces;
    private InspectionType[] inspectionTypes;
    private MeasurementType[] measurementTypes;
    private RegularCondition[] regularConditions;
    private DeviceName[] deviceNames;

    private final DeviceService deviceService;
    private final InspectionPlaceService inspectionPlaceService;
    private final InspectionTypeService inspectionTypeService;
    private final MeasurementTypeService measurementTypeService;
    private final RegularConditionService regularConditionService;
    private final DeviceNameService deviceNameService;
    private final DeviceLocationService deviceLocationService;

    public MainFrame(DeviceService deviceService, InspectionPlaceService inspectionPlaceService,
                     InspectionTypeService inspectionTypeService, MeasurementTypeService measurementTypeService,
                     RegularConditionService regularConditionService, DeviceNameService deviceNameService, DeviceLocationService deviceLocationService) {
        this.deviceService = deviceService;
        this.inspectionPlaceService = inspectionPlaceService;
        this.inspectionTypeService = inspectionTypeService;
        this.measurementTypeService = measurementTypeService;
        this.regularConditionService = regularConditionService;
        this.deviceNameService = deviceNameService;
        this.deviceLocationService = deviceLocationService;
    }

    public void run() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.invokeLater(() ->
                    new MainFrame(deviceService, inspectionPlaceService, inspectionTypeService,
                            measurementTypeService, regularConditionService, deviceNameService, deviceLocationService).init());
        } catch (Exception e) {
//            LOG.error("Критическая ошибка отображения формы", e);
            JOptionPane.showMessageDialog(this, "Критическая ошибка отображения формы\nОбратитесь в поддержку",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void init() {
        configureTableModel();
        configureTable();
        configureMenu();
        configurePopupMenu();
        printMainInterface();

    }

    private void configurePopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem addMenuItem = new JMenuItem(ADD);
        addMenuItem.addActionListener(e -> addNewDevice());
        JMenuItem updateMenuItem = new JMenuItem(UPDATE);
        updateMenuItem.addActionListener(e -> updateDevice());
        JMenuItem copyMenuItem = new JMenuItem(COPY);
        copyMenuItem.addActionListener(e -> copyDevice());
        JMenuItem deleteMenuItem = new JMenuItem(DELETE);
        deleteMenuItem.addActionListener(e -> deleteDevice());

        popupMenu.add(addMenuItem);
        popupMenu.add(updateMenuItem);
        popupMenu.add(copyMenuItem);
        popupMenu.add(deleteMenuItem);

        table.setComponentPopupMenu(popupMenu);
    }

    private void configureMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mainMenu = new JMenu(MENU);
        menuBar.add(mainMenu);
        JMenuItem addDeviceMenuItem = new JMenuItem(ADD);
        mainMenu.add(addDeviceMenuItem);
        addDeviceMenuItem.addActionListener(e -> addNewDevice());

        JMenu settingsMenu = new JMenu(SETTINGS);
        menuBar.add(settingsMenu);
        JMenuItem inspectionPlaceMenuItem = new JMenuItem(INSPECTION_PLACE);
        settingsMenu.add(inspectionPlaceMenuItem);
        inspectionPlaceMenuItem.addActionListener(e -> openInspectionPlaceSettings());
        JMenuItem inspectionTypeMenuItem = new JMenuItem(INSPECTION_TYPE);
        settingsMenu.add(inspectionTypeMenuItem);
        inspectionTypeMenuItem.addActionListener(e -> openInspectionTypeSettings());
        JMenuItem measurementTypeMenuItem = new JMenuItem(MEASUREMENT_TYPE);
        settingsMenu.add(measurementTypeMenuItem);
        measurementTypeMenuItem.addActionListener(e -> openMeasurementTypeSettings());
        JMenuItem regularConditionMenuItem = new JMenuItem(REGULAR_CONDITION);
        settingsMenu.add(regularConditionMenuItem);
        regularConditionMenuItem.addActionListener(e -> openRegularConditionSettings());
        JMenuItem deviceNameMenuItem = new JMenuItem(DEVICE_NAME);
        settingsMenu.add(deviceNameMenuItem);
        deviceNameMenuItem.addActionListener(e -> openDeviceNameSettings());
        JMenuItem deviceLocationMenuItem = new JMenuItem(DEVICE_LOCATION);
        settingsMenu.add(deviceLocationMenuItem);
        deviceLocationMenuItem.addActionListener(e -> openDeviceLocationSettings());


    }

    private void openDeviceLocationSettings() {
        List<String> deviceLoactions = deviceLocationService.findAll().stream()
                .map(DeviceLocation::getDeviceLocation)
                .collect(Collectors.toList());
        new DeviceLocationSettingsFrame(deviceLocationService, deviceLoactions, DEVICE_LOCATION, this).init();
    }

    private void openInspectionPlaceSettings() {
        List<String> inspectionPlaces = inspectionPlaceService.findAll().stream()
                .map(InspectionPlace::getInspectionPlace)
                .collect(Collectors.toList());
        new InspectionPlaceSettingsFrame(inspectionPlaceService, inspectionPlaces, INSPECTION_PLACE, this).init();
    }

    private void openInspectionTypeSettings() {
        List<String> inspectionTypes = inspectionTypeService.findAll().stream()
                .map(InspectionType::getInspectionType)
                .collect(Collectors.toList());
        new InspectionTypeSettingsFrame(inspectionTypeService, inspectionTypes, INSPECTION_TYPE, this).init();
    }

    private void openMeasurementTypeSettings() {
        List<String> measurementTypes = measurementTypeService.findAll().stream()
                .map(MeasurementType::getMeasurementType)
                .collect(Collectors.toList());
        new MeasurementTypeSettingsFrame(measurementTypeService, measurementTypes, MEASUREMENT_TYPE, this).init();
    }

    private void openRegularConditionSettings() {
        List<String> regularConditions = regularConditionService.findAll().stream()
                .map(RegularCondition::getRegularCondition)
                .collect(Collectors.toList());
        new RegularConditionSettingsFrame(regularConditionService, regularConditions, REGULAR_CONDITION, this).init();
    }

    private void openDeviceNameSettings() {
        List<String> deviceNames = deviceNameService.findAll().stream()
                .map(DeviceName::getDeviceName)
                .collect(Collectors.toList());
        new DeviceNameSettingsFrame(deviceNameService, deviceNames, DEVICE_NAME, this).init();
    }

    private void addNewDevice() {
        configureDeviceForm(false);
        deviceFrame.setUpdate(false);
        deviceFrame.showNewFrame();
    }

    private void copyDevice() {
        configureDeviceForm(false);
        deviceFrame.setUpdate(true);
        Device device = findDeviceForRow();
        deviceFrame.showCopyFrame(device);
    }

    private void updateDevice() {
        configureDeviceForm(false);
        deviceFrame.setUpdate(true);
        Device device = findDeviceForRow();
        deviceFrame.showExistFrame(device);
    }

    private void deleteDevice() {
        configureDeviceForm(false);
        int option = JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить этот прибор?",
                "Удаление прибора", JOptionPane.YES_NO_OPTION);
        if (option == 0) {
            Device device = findDeviceForRow();
            deviceTableModel.deleteDevice(device.getId());
        }
    }

    private Device findDeviceForRow() {
        int rowNumber = table.getSelectedRow();
        String name = ((DeviceName) table.getValueAt(rowNumber, 0)).getDeviceName();
        String factoryNumber = (String) table.getValueAt(rowNumber, 4);

        return deviceService.findByNameAndFactoryNumber(name, factoryNumber);
    }

    private void configureDeviceForm(boolean isReconfig) {
        if (deviceFrame == null || isReconfig) {
            deviceLocations = deviceLocationService.findAll().toArray(new DeviceLocation[0]);
            inspectionPlaces = inspectionPlaceService.findAll().toArray(new InspectionPlace[0]);
            inspectionTypes = inspectionTypeService.findAll().toArray(new InspectionType[0]);
            measurementTypes = measurementTypeService.findAll().toArray(new MeasurementType[0]);
            regularConditions = regularConditionService.findAll().toArray(new RegularCondition[0]);
            deviceNames = deviceNameService.findAll().toArray(new DeviceName[0]);
            deviceFrame = new DeviceFrame(deviceService, deviceTableModel,
                    deviceLocations, inspectionPlaces, inspectionTypes,
                    measurementTypes, regularConditions, deviceNames);
        }
    }

    private void printMainInterface() {
        this.setLayout(new BorderLayout());
        this.add(deviceScrollPane);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void configureTable() {
        font = new Font(null, Font.PLAIN, 18);
        table = new JTable(deviceTableModel);
        table.setSelectionBackground(new Color(180, 180, 180));
//        table.setShowVerticalLines(false);
        table.setRowHeight(18 + 5);
        table.setFont(font);
        table.getTableHeader().setFont(font);
        table.getTableHeader().setMaximumSize(new Dimension(50, 50));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(188, 188, 188));
//        table.getTableHeader().setForeground(new Color(255, 255, 255));
        table.getTableHeader().setPreferredSize(new Dimension(0, 50));
        deviceScrollPane = new JScrollPane(table);
        table.addMouseListener(new TableMouseListener(table));
    }

    private void configureTableModel() {
        List<Device> devices = deviceService.findAll();
        deviceTableModel = new DeviceTableModel(deviceService, devices);
    }

    @Override
    public void reconfigureDeviceFrame() {
        configureDeviceForm(true);
    }
}
