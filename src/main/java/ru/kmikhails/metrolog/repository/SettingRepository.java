package ru.kmikhails.metrolog.repository;

import ru.kmikhails.metrolog.domain.Setting;
import ru.kmikhails.metrolog.exception.DataBaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingRepository extends AbstractCrudRepository<Setting> {
    private static final String SAVE_QUERY = "INSERT INTO settings (type, description, settings_value) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE settings SET settings_value = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM settings WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT id, type, description, settings_value FROM settings";
    private static final String FIND_BY_NAME_QUERY = "";

    public SettingRepository(DataSource dataSource) {
        super(dataSource, SAVE_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY, FIND_ALL_QUERY, FIND_BY_NAME_QUERY);
    }

    public void update(Long settingId, String settingValue) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setString(1, settingValue);
            statement.setLong(2, settingId);
            statement.executeUpdate();

        } catch (SQLException e) {
//         LOG.error(e);
            throw new DataBaseException(e);
        }
    }

    @Override
    protected Setting mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Setting setting = new Setting();
        setting.setId(resultSet.getLong("id"));
        setting.setType(resultSet.getString("type"));
        setting.setDescription(resultSet.getString("description"));
        setting.setValue(resultSet.getString("settings_value"));
        
        return setting;
    }

    @Override
    protected void insert(PreparedStatement statement, Setting setting) throws SQLException {
        statement.setString(1, setting.getType());
        statement.setString(2, setting.getDescription());
        statement.setString(3, setting.getValue());
    }
}
