package ru.kmikhails.metrolog.repository;

import ru.kmikhails.metrolog.exception.DataBaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudRepository<E> {

    protected final DataSource dataSource;
    protected final String saveQuery;
    protected final String updateQuery;
    protected final String deleteQuery;
    protected final String findAllQuery;
    protected final String findByNameQuery;

    protected AbstractCrudRepository(DataSource dataSource, String saveQuery, String updateQuery, String deleteQuery,
                                     String findAllQuery, String findByNameQuery) {
        this.dataSource = dataSource;
        this.saveQuery = saveQuery;
        this.updateQuery = updateQuery;
        this.deleteQuery = deleteQuery;
        this.findAllQuery = findAllQuery;
        this.findByNameQuery = findByNameQuery;
    }

    public void save(E entity) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement addStatement = connection.prepareStatement(saveQuery)) {

            insert(addStatement, entity);
            addStatement.executeUpdate();

        } catch (SQLException e) {
//         LOG.error(e);
            throw new DataBaseException(e);
        }
    }

    public void update(E entity, Long id, int parameterIndex) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(updateQuery)) {

            insert(statement, entity);
            statement.setLong(parameterIndex, id);
            statement.executeUpdate();

        } catch (SQLException e) {
//         LOG.error(e);
            throw new DataBaseException(e);
        }
    }

    public void delete(Long id) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(deleteQuery)) {

            statement.setLong(1, id);
            if (statement.executeUpdate() == 0) {
                throw new IllegalArgumentException("Entity with id: " + id + " does not exist");
            }
        } catch (SQLException e) {
//         LOG.error(e);
            throw new DataBaseException(e);
        }
    }

    public List<E> findAll() {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(findAllQuery)) {

            try (final ResultSet resultSet = statement.executeQuery()) {
                List<E> entities = new ArrayList<>();
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

    public E findByName(String value) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(findByNameQuery)) {

            statement.setString(1, value);

            try (final ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToEntity(resultSet);
                }
            }
        } catch (SQLException e) {
//         LOG.error(e);
            throw new DataBaseException(e);
        }
        return null;
    }

    protected abstract E mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    protected abstract void insert(PreparedStatement addStatement, E entity) throws SQLException;
}
