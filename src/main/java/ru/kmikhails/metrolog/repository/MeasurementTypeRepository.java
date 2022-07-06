package ru.kmikhails.metrolog.repository;

import ru.kmikhails.metrolog.domain.MeasurementType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MeasurementTypeRepository extends AbstractCrudRepository<MeasurementType> {
    private static final String SAVE_QUERY = "INSERT INTO measurement_types (measurement_type) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE measurement_types SET measurement_type = ? WHERE measurement_type_id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM measurement_types WHERE measurement_type_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT measurement_type_id, measurement_type FROM measurement_types";
    private static final String FIND_BY_NAME_QUERY = "SELECT measurement_type_id, measurement_type FROM measurement_types " +
            "WHERE measurement_type = ?";

    public MeasurementTypeRepository(DataSource dataSource) {
        super(dataSource, SAVE_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY, FIND_ALL_QUERY, FIND_BY_NAME_QUERY);
    }

    public void update(MeasurementType measurementType) {
        super.update(measurementType, measurementType.getId(), 2);
    }

    @Override
    protected MeasurementType mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        MeasurementType measurementType = new MeasurementType();
        measurementType.setId(resultSet.getLong("measurement_type_id"));
        measurementType.setMeasurementType(resultSet.getString("measurement_type"));

        return measurementType;
    }

    @Override
    protected void insert(PreparedStatement statement, MeasurementType measurementType) throws SQLException {
        statement.setString(1, measurementType.getMeasurementType());
    }
}
