package ru.kmikhails.metrolog.repository;

import ru.kmikhails.metrolog.domain.InspectionPlace;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InspectionPlaceRepository extends AbstractCrudRepository<InspectionPlace> {
    private static final String SAVE_QUERY = "INSERT INTO inspection_places (inspection_place) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE inspection_places SET inspection_place = ? WHERE inspection_place_id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM inspection_places WHERE inspection_place_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT inspection_place_id, inspection_place FROM inspection_places";
    private static final String FIND_BY_NAME_QUERY = "SELECT inspection_place_id, inspection_place FROM inspection_places " +
            "WHERE inspection_place = ?";

    public InspectionPlaceRepository(DataSource dataSource) {
        super(dataSource, SAVE_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY, FIND_ALL_QUERY, FIND_BY_NAME_QUERY);
    }

    public void update(InspectionPlace inspectionPlace) {
        super.update(inspectionPlace, inspectionPlace.getId(), 2);
    }

    @Override
    protected InspectionPlace mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        InspectionPlace inspectionPlace = new InspectionPlace();
        inspectionPlace.setId(resultSet.getLong("inspection_place_id"));
        inspectionPlace.setInspectionPlace(resultSet.getString("inspection_place"));

        return inspectionPlace;
    }

    @Override
    protected void insert(PreparedStatement statement, InspectionPlace inspectionPlace) throws SQLException {
        statement.setString(1, inspectionPlace.getInspectionPlace());
    }
}
