package ru.kmikhails.metrolog.repository;

import ru.kmikhails.metrolog.domain.RegularCondition;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegularConditionRepository extends AbstractCrudRepository<RegularCondition> {
    private static final String SAVE_QUERY = "INSERT INTO regular_conditions (regular_condition) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE regular_conditions SET regular_condition = ? WHERE regular_condition_id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM regular_conditions WHERE regular_condition_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT regular_condition_id, regular_condition FROM regular_conditions";
    private static final String FIND_BY_NAME_QUERY = "SELECT regular_condition_id, regular_condition FROM regular_conditions " +
            "WHERE regular_condition = ?";

    public RegularConditionRepository(DataSource dataSource) {
        super(dataSource, SAVE_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY, FIND_ALL_QUERY, FIND_BY_NAME_QUERY);
    }

    public void update(RegularCondition regularCondition) {
        super.update(regularCondition, regularCondition.getId(), 2);
    }

    @Override
    protected RegularCondition mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        RegularCondition regularCondition = new RegularCondition();
        regularCondition.setId(resultSet.getLong("regular_condition_id"));
        regularCondition.setRegularCondition(resultSet.getString("regular_condition"));

        return regularCondition;
    }

    @Override
    protected void insert(PreparedStatement statement, RegularCondition regularCondition) throws SQLException {
        statement.setString(1, regularCondition.getRegularCondition());
    }
}
