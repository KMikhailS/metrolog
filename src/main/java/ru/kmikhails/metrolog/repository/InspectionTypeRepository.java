package ru.kmikhails.metrolog.repository;

import ru.kmikhails.metrolog.domain.InspectionType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InspectionTypeRepository extends AbstractCrudRepository<InspectionType> {
    private static final String SAVE_QUERY = "INSERT INTO inspection_types (inspection_type) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE inspection_types SET inspection_type = ? WHERE inspection_type_id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM inspection_types WHERE inspection_type_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT inspection_type_id, inspection_type FROM inspection_types";
    private static final String FIND_BY_NAME_QUERY = "SELECT inspection_type_id, inspection_type FROM inspection_types " +
            "WHERE inspection_type = ?";

    public InspectionTypeRepository(DataSource dataSource) {
        super(dataSource, SAVE_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY, FIND_ALL_QUERY, FIND_BY_NAME_QUERY);
    }

    public void update(InspectionType inspectionType) {
        super.update(inspectionType, inspectionType.getId(), 2);
    }

    @Override
    protected InspectionType mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        InspectionType inspectionType = new InspectionType();
        inspectionType.setId(resultSet.getLong("inspection_type_id"));
        inspectionType.setInspectionType(resultSet.getString("inspection_type"));

        return inspectionType;
    }

    @Override
    protected void insert(PreparedStatement statement, InspectionType inspectionType) throws SQLException {
        statement.setString(1, inspectionType.getInspectionType());
    }
}
