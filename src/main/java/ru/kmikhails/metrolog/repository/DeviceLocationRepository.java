package ru.kmikhails.metrolog.repository;

import ru.kmikhails.metrolog.domain.DeviceLocation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeviceLocationRepository extends AbstractCrudRepository<DeviceLocation> {
    private static final String SAVE_QUERY = "INSERT INTO device_locations (device_location) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE device_locations SET device_location = ? WHERE device_location_id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM device_locations WHERE device_location_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT device_location_id, device_location FROM device_locations";
    private static final String FIND_BY_NAME_QUERY = "SELECT device_location_id, device_location FROM device_locations " +
            "WHERE device_location = ?";

    public DeviceLocationRepository(DataSource dataSource) {
        super(dataSource, SAVE_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY, FIND_ALL_QUERY, FIND_BY_NAME_QUERY);
    }

    public void update(DeviceLocation deviceLocation) {
        super.update(deviceLocation, deviceLocation.getId(), 2);
    }

    @Override
    protected DeviceLocation mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        DeviceLocation deviceLocation = new DeviceLocation();
        deviceLocation.setId(resultSet.getLong("device_location_id"));
        deviceLocation.setDeviceLocation(resultSet.getString("device_location"));

        return deviceLocation;
    }

    @Override
    protected void insert(PreparedStatement statement, DeviceLocation deviceLocation) throws SQLException {
        statement.setString(1, deviceLocation.getDeviceLocation());
    }
}
