package ru.kmikhails.metrolog.repository;

import ru.kmikhails.metrolog.domain.DeviceName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeviceNameRepository extends AbstractCrudRepository<DeviceName> {
    private static final String SAVE_QUERY = "INSERT INTO device_names (device_name) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE device_names SET device_name = ? WHERE device_name_id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM device_names WHERE device_name_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT device_name_id, device_name FROM device_names";
    private static final String FIND_BY_NAME_QUERY = "SELECT device_name_id, device_name FROM device_names " +
            "WHERE device_name = ?";

    public DeviceNameRepository(DataSource dataSource) {
        super(dataSource, SAVE_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY, FIND_ALL_QUERY, FIND_BY_NAME_QUERY);
    }

    public void update(DeviceName deviceName) {
        super.update(deviceName, deviceName.getId(), 2);
    }

    @Override
    protected DeviceName mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        DeviceName device_name = new DeviceName();
        device_name.setId(resultSet.getLong("device_name_id"));
        device_name.setDeviceName(resultSet.getString("device_name"));

        return device_name;
    }

    @Override
    protected void insert(PreparedStatement statement, DeviceName deviceName) throws SQLException {
        statement.setString(1, deviceName.getDeviceName());
    }
}
