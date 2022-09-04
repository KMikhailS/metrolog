package ru.kmikhails.metrolog.repository;

import ru.kmikhails.metrolog.domain.*;
import ru.kmikhails.metrolog.exception.DataBaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeviceRepository  {
    private static final String SAVE_QUERY = "INSERT INTO devices (device_name_id, reg_number, type, range, category, factory_number, " +
            "last_inspection_date, next_inspection_date, inspection_frequency, inspection_place_id, " +
            "inspection_protocol_number, inspection_type_id, device_location_id, regular_condition_id, " +
            "measurement_type_id, responsible, history) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE devices SET device_name_id = ?, reg_number = ?, type = ?, range = ?, " +
            "category = ?, factory_number = ?, last_inspection_date = ?, next_inspection_date = ?, inspection_frequency = ?, " +
            "inspection_place_id = ?, inspection_protocol_number = ?, inspection_type_id = ?, device_location_id = ?, " +
            "regular_condition_id = ?, measurement_type_id = ?, responsible = ?, history = ? WHERE device_id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM devices WHERE device_id = ?";
    private static final String SELECT_DEVICE_QUERY = "SELECT d.device_id, dn.device_name_id, d.device_name_id, dn.device_name, " +
            "d.reg_number, d.type, d.range, d.category, d.factory_number, d.last_inspection_date, d.next_inspection_date, " +
            "d.inspection_frequency, d.inspection_place_id, ip.inspection_place, d.inspection_protocol_number, " +
            "d.inspection_type_id, it.inspection_type, d.device_location_id, dl.device_location, d.regular_condition_id, " +
            "rc.regular_condition, d.measurement_type_id, mt.measurement_type, d.responsible, " +
            "d.history " +
            "FROM devices d " +
            "LEFT JOIN device_locations dl " +
            "ON d.device_location_id = dl.device_location_id " +
            "LEFT JOIN inspection_places ip " +
            "ON d.inspection_place_id = ip.inspection_place_id " +
            "LEFT JOIN inspection_types it " +
            "ON d.inspection_type_id = it.inspection_type_id " +
            "LEFT JOIN measurement_types mt " +
            "ON d.measurement_type_id = mt.measurement_type_id " +
            "LEFT JOIN regular_conditions rc " +
            "ON d.regular_condition_id = rc.regular_condition_id " +
            "LEFT JOIN device_names dn " +
            "ON d.device_name_id = dn.device_name_id ";
    private static final String FIND_ALL_QUERY = SELECT_DEVICE_QUERY;
    private static final String FIND_BY_ID_QUERY = SELECT_DEVICE_QUERY + "WHERE d.device_id = ?";
    private static final String FIND_BY_NAME_AND_FACTORY_NUMBER_QUERY =
            SELECT_DEVICE_QUERY + "WHERE dn.device_name = ? AND d.factory_number = ?";

    private final DataSource dataSource;

    public DeviceRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void save(Device device) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement addStatement = connection.prepareStatement(SAVE_QUERY)) {

            insert(addStatement, device);
            addStatement.executeUpdate();

        } catch (SQLException e) {
//         LOG.error(e);
            throw new DataBaseException(e);
        }
    }

    public void update(Device device) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            insert(statement, device);
            statement.setLong(18, device.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
//         LOG.error(e);
            throw new DataBaseException(e);
        }
    }

    public void delete(Long id) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_QUERY)) {

            statement.setLong(1, id);
            if (statement.executeUpdate() == 0) {
                throw new IllegalArgumentException("Device with id: " + id + " does not exist");
            }
        } catch (SQLException e) {
//         LOG.error(e);
            throw new DataBaseException(e);
        }
    }

    public List<Device> findAll() {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY)) {

            try (final ResultSet resultSet = statement.executeQuery()) {
                List<Device> entities = new ArrayList<>();
                while (resultSet.next()) {
                    entities.add(mapResultSetToEntity(resultSet));
                }
                return entities;
            }
        } catch (SQLException e) {
//         LOG.error(e);
            throw new DataBaseException(e);
        }
    }


    public Optional<Device> findById(Long id) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {

            statement.setLong(1, id);

            try (final ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEntity(resultSet));
                }
            }
        } catch (SQLException e) {
//            LOG.error(e);
            throw new DataBaseException(e);
        }
        return Optional.empty();
    }

    public Optional<Device> findByNameAndFactoryNumber(String name, String factoryNumber) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME_AND_FACTORY_NUMBER_QUERY)) {

            statement.setString(1, name);
            statement.setString(2, factoryNumber);

            try (final ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEntity(resultSet));
                }
            }
        } catch (SQLException e) {
//            LOG.error(e);
            throw new DataBaseException(e);
        }
        return Optional.empty();
    }

    private void insert(PreparedStatement statement, Device device) throws SQLException {
        statement.setLong(1, device.getName().getId());
        statement.setString(2, device.getRegNumber());
        statement.setString(3, device.getType());
        statement.setString(4, device.getRange());
        statement.setString(5, device.getCategory());
        statement.setString(6, device.getFactoryNumber());
        if (device.getLastInspectionDate() != null) {
            statement.setString(7, device.getLastInspectionDate().toString());
        } else {
            statement.setString(7, null);
        }
        if (device.getNextInspectionDate() != null) {
            statement.setString(8, device.getNextInspectionDate().toString());
        } else {
            statement.setString(8, null);
        }
        statement.setInt(9, device.getInspectionFrequency());
        if (device.getInspectionPlace() != null) {
            statement.setLong(10, device.getInspectionPlace().getId());
        }
        statement.setString(11, device.getInspectionProtocolNumber());
        if (device.getInspectionType() != null) {
            statement.setLong(12, device.getInspectionType().getId());
        }
        if (device.getDeviceLocation() != null) {
            statement.setLong(13, device.getDeviceLocation().getId());
        }
        if (device.getRegularCondition() != null) {
            statement.setLong(14, device.getRegularCondition().getId());
        }
        if (device.getMeasurementType() != null) {
            statement.setLong(15, device.getMeasurementType().getId());
        }
        statement.setString(16, device.getResponsible());
        statement.setString(17, device.getHistory());
    }

    private Device mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Device device = new Device();
        device.setId(resultSet.getLong("device_id"));
        DeviceName deviceName = new DeviceName();
        deviceName.setId(resultSet.getLong("device_name_id"));
        deviceName.setDeviceName(resultSet.getString("device_name"));
        device.setName(deviceName);
        device.setRegNumber(resultSet.getString("reg_number"));
        device.setType(resultSet.getString("type"));
        device.setRange(resultSet.getString("range"));
        device.setCategory(resultSet.getString("category"));
        device.setFactoryNumber(resultSet.getString("factory_number"));
        device.setLastInspectionDate(resultSet.getString("last_inspection_date") != null ?
                LocalDate.parse(resultSet.getString("last_inspection_date")) : null);
        device.setNextInspectionDate(resultSet.getString("next_inspection_date") != null ?
                LocalDate.parse(resultSet.getString("next_inspection_date")) : null);
        device.setInspectionFrequency(resultSet.getInt("inspection_frequency"));
        InspectionPlace inspectionPlace = new InspectionPlace();
        inspectionPlace.setId(resultSet.getLong("inspection_place_id"));
        inspectionPlace.setInspectionPlace(resultSet.getString("inspection_place"));
        device.setInspectionPlace(inspectionPlace);
        device.setInspectionProtocolNumber(resultSet.getString("inspection_protocol_number"));
        InspectionType inspectionType = new InspectionType();
        inspectionType.setId(resultSet.getLong("inspection_type_id"));
        inspectionType.setInspectionType(resultSet.getString("inspection_type"));
        device.setInspectionType(inspectionType);
        DeviceLocation deviceLocation = new DeviceLocation();
        deviceLocation.setId(resultSet.getLong("device_location_id"));
        deviceLocation.setDeviceLocation(resultSet.getString("device_location"));
        device.setDeviceLocation(deviceLocation);
        RegularCondition regularCondition = new RegularCondition();
        regularCondition.setId(resultSet.getLong("regular_condition_id"));
        regularCondition.setRegularCondition(resultSet.getString("regular_condition"));
        device.setRegularCondition(regularCondition);
        MeasurementType measurementType = new MeasurementType();
        measurementType.setId(resultSet.getLong("measurement_type_id"));
        measurementType.setMeasurementType(resultSet.getString("measurement_type"));
        device.setMeasurementType(measurementType);
        device.setResponsible(resultSet.getString("responsible"));
        device.setHistory(resultSet.getString("history"));

        return device;
    }
}
