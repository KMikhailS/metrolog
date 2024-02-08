package ru.kmikhails.metrolog;

import ru.kmikhails.metrolog.repository.*;
import ru.kmikhails.metrolog.service.*;
import ru.kmikhails.metrolog.view.MainFrame;
import ru.kmikhails.metrolog.view.util.CustomAction;

public class MetrologApplication {
    public static void main(String[] args) {
        DataSource dataSource = new DataSource();
        DeviceRepository deviceRepository = new DeviceRepository(dataSource);
        DeviceService deviceService = new DeviceService(deviceRepository);
        InspectionPlaceRepository inspectionPlaceRepository = new InspectionPlaceRepository(dataSource);
        InspectionPlaceService inspectionPlaceService = new InspectionPlaceService(inspectionPlaceRepository);
        InspectionTypeRepository inspectionTypeRepository = new InspectionTypeRepository(dataSource);
        InspectionTypeService inspectionTypeService = new InspectionTypeService(inspectionTypeRepository);
        MeasurementTypeRepository measurementTypeRepository = new MeasurementTypeRepository(dataSource);
        MeasurementTypeService measurementTypeService = new MeasurementTypeService(measurementTypeRepository);
        RegularConditionRepository regularConditionRepository = new RegularConditionRepository(dataSource);
        RegularConditionService regularConditionService = new RegularConditionService(regularConditionRepository);
        DeviceNameRepository responsibleRepository = new DeviceNameRepository(dataSource);
        DeviceNameService deviceNameService = new DeviceNameService(responsibleRepository);
        DeviceLocationRepository deviceLocationRepository = new DeviceLocationRepository(dataSource);
        DeviceLocationService deviceLocationService = new DeviceLocationService(deviceLocationRepository);
        CustomAction customAction = new CustomAction(deviceService);

        MainFrame mainFrame = new MainFrame(deviceService, inspectionPlaceService, inspectionTypeService,
                measurementTypeService, regularConditionService, deviceNameService, deviceLocationService, customAction);
        mainFrame.run();
    }
}
