package orm;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static orm.MyConnector.getConnection;

public class EntityManager<E> implements DbContext<E> {
    private static final String INSERT_QUERY_FORMAT = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String DELETE_RECORD_BY_CONDITION_QUERY_FORMAT = "DELETE FROM %s WHERE %s = %s";
    private static final String UPDATE_QUERY_BY_ID_FORMAT = "UPDATE %s e SET %s WHERE e.id = %d";
    private static final String FIND_FIRST_WITH_CONDITION_QUERY = "SELECT * FROM %s %s LIMIT 1";
    private static final String FIND_ALL_WITH_CONDITION_QUERY = "SELECT * FROM %s %s";
    private static final String FIND_FIRST_QUERY = "SELECT * FROM %s LIMIT 1";
    private static final String FIND_ALL_QUERY = "SELECT * FROM %s";
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE %s (id INT PRIMARY KEY AUTO_INCREMENT, %s)";
    private static final String ALTER_TABLE_QUERY = "ALTER TABLE %s %s";
    private static final String ADD_COLUMN_FORMAT = "ADD COLUMN %s %s";
    private static final String DROP_COLUMN_FORMAT = "DROP COLUMN %s";
    private static final String GET_ALL_COLUMN_NAMES_BY_TABLE_NAME_QUERY =
            "SELECT `COLUMN_NAME` FROM `INFORMATION_SCHEMA`.`COLUMNS` " +
                    "WHERE `TABLE_SCHEMA` = 'custom-orm' AND `COLUMN_NAME` != 'id' AND `TABLE_NAME` = ?";

    private static final String ID_COLUM_MISSING_MESSAGE = "Entity missing an Id column";
    private static final String CLASS_MUST_BE_ENTITY_MESSAGE = "Class must be Entity";
    private static final String UPDATE_VALUE_FORMAT = "%s = %s";
    private static final String CREATE_VALUE_FORMAT = "%s %s";
    private static final String WHERE_KEY_WORD = "WHERE";
    private static final String VARCHAR = "VARCHAR(45)";
    private static final String INT = "INT";
    private static final String DATE = "DATE";

    private final Connection connection;

    public EntityManager() throws SQLException {
        this.connection = getConnection();
    }

    @Override
    public boolean persist(E entity) throws SQLException, IllegalAccessException {
        final Field idColumn = getIdColumn(entity.getClass());
        idColumn.setAccessible(true);

        final Object idValue = idColumn.get(entity);

        if (idValue == null || (long) idValue <= 0) {
            return doInsert(entity);
        } else {
            return doUpdate(entity, idColumn);
        }
    }

    @Override
    public Iterable<E> find(Class<E> table) throws SQLException,
            InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException {

        final String tableName = getTableName(table);

        final PreparedStatement findStatement =
                connection.prepareStatement(String.format(FIND_ALL_QUERY, tableName));

        return getPOJOs(findStatement, table);
    }

    @Override
    public Iterable<E> find(Class<E> table, String condition)
            throws SQLException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {

        final String tableName = getTableName(table);

        final PreparedStatement findStatement =
                connection.prepareStatement(String.format(FIND_ALL_WITH_CONDITION_QUERY, tableName
                        , WHERE_KEY_WORD + " " + condition));

        return getPOJOs(findStatement, table);

    }

    @Override
    public E findFirst(Class<E> table)
            throws SQLException, InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        final String tableName = getTableName(table);

        final PreparedStatement findFirstStatement =
                connection.prepareStatement(String.format(FIND_FIRST_QUERY, tableName));

        return getPOJO(findFirstStatement, table);
    }

    @Override
    public E findFirst(Class<E> table, String condition) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        final String tableName = getTableName(table);

        final PreparedStatement findFirstStatement =
                connection.prepareStatement(String.format(FIND_FIRST_WITH_CONDITION_QUERY
                        , tableName
                        , WHERE_KEY_WORD + " " + condition));

        return getPOJO(findFirstStatement, table);
    }

    @Override
    public void doCreate(Class<E> entity) throws SQLException {
        final String tableName = getTableName(entity);

        final List<KeyValuePair> fieldsWithTypes = getAllFieldsAndTypesInKeyValuePairs(entity);

        final String fieldsWithTypesFormatted = fieldsWithTypes.stream()
                .map(keyValuePair -> String.format(CREATE_VALUE_FORMAT, keyValuePair.key, keyValuePair.value))
                .collect(Collectors.joining(", "));

        final PreparedStatement createStatement =
                connection.prepareStatement(String.format(CREATE_TABLE_QUERY, tableName, fieldsWithTypesFormatted));

        createStatement.execute();
    }

    @Override
    public void doAlter(Class<E> entity) throws SQLException {
        final String tableName = getTableName(entity);
        final String columnStatementForNewFields = addColumnsStatementsForNewFields(entity, tableName);

        final String alterQuery = String.format(ALTER_TABLE_QUERY, tableName, columnStatementForNewFields);

        final PreparedStatement alterStatement = connection.prepareStatement(alterQuery);

        alterStatement.execute();

    }

    @Override
    public void doDelete(E entity) throws SQLException, IllegalAccessException {
        final String tableName = getTableName(entity.getClass());

        final Field idField = getIdColumn(entity.getClass());

        final String idName = getSQLColumnName(idField);
        final Object idValue = getFieldValue(entity, idField);

        final String deleteQuery = String.format(DELETE_RECORD_BY_CONDITION_QUERY_FORMAT, tableName, idName, idValue);

        final PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);

        deleteStatement.execute();
    }

    private Object getFieldValue(E entity, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(entity);
    }

    private String addColumnsStatementsForNewFields(Class<E> entity, String tableName) throws SQLException {
        final Set<String> sqlColumns = getSQLColumnNames(entity, tableName);
        final List<Field> allFieldsWithoutId = getAllFieldsWithoutId(entity);

        List<String> newFieldsStatement = new ArrayList<>();

        for (Field field : allFieldsWithoutId) {
            final String fieldName = getSQLColumnName(field);

            if (sqlColumns.contains(fieldName)) {
//                final String dropStatement = String.format(DROP_COLUMN_FORMAT, fieldName);
//                newFieldsStatement.add(dropStatement);
                continue;
            }

            final String sqlType = getSQLType(field.getType());

            final String addStatement = String.format(ADD_COLUMN_FORMAT, fieldName, sqlType);

            newFieldsStatement.add(addStatement);
        }

        return String.join(", ", newFieldsStatement);
    }

    private Set<String> getSQLColumnNames(Class<E> entity, String tableName) throws SQLException {
        Set<String> allFields = new HashSet<>();

        final PreparedStatement getAllFieldsStatement =
                connection.prepareStatement(GET_ALL_COLUMN_NAMES_BY_TABLE_NAME_QUERY);
        getAllFieldsStatement.setString(1, tableName);
        ResultSet resultSet = getAllFieldsStatement.executeQuery();

        while (resultSet.next()) {
            allFields.add(resultSet.getString(1));
        }

        return allFields;
    }

    private List<KeyValuePair> getAllFieldsAndTypesInKeyValuePairs(Class<E> entity) {
        return getAllFieldsWithoutId(entity)
                .stream()
                .map(field -> new KeyValuePair(getSQLColumnName(field), getSQLType(field.getType())))
                .toList();
    }

    private String getSQLType(Class<?> type) {
        if ((type == Integer.class || type == int.class)) {
            return INT;
        } else if (type == LocalDate.class) {
            return DATE;
        }
        return VARCHAR;
    }

    private String getSQLColumnName(Field field) {
        return field.getAnnotationsByType(Column.class)[0].name();
    }

    private List<Field> getAllFieldsWithoutId(Class<E> entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class) && f.isAnnotationPresent(Column.class))
                .toList();
    }

    private boolean doInsert(E entity) throws SQLException {
        final String tableName = getTableName(entity.getClass());

        final List<KeyValuePair> keyValuePairs = getKeyValuePairs(entity);

        final String fields = keyValuePairs.stream()
                .map(KeyValuePair::key)
                .collect(Collectors.joining(", "));

        final String values = keyValuePairs.stream()
                .map(KeyValuePair::value)
                .collect(Collectors.joining(", "));

        final String insertQuery = String.format(INSERT_QUERY_FORMAT,
                tableName, fields, values);

        return this.connection.prepareStatement(insertQuery).execute();
    }

    private boolean doUpdate(E entity, Field idColumn) throws SQLException, IllegalAccessException {
        final String tableName = getTableName(entity.getClass());

        final List<KeyValuePair> keyValuePairs = getKeyValuePairs(entity);

        final String updateValues = keyValuePairs.stream()
                .map(keyValuePair -> String.format(UPDATE_VALUE_FORMAT, keyValuePair.key, keyValuePair.value))
                .collect(Collectors.joining(","));

        final long idValue = Long.parseLong(idColumn.get(entity).toString());

        final String updateQuery = String.format(UPDATE_QUERY_BY_ID_FORMAT, tableName, updateValues, idValue);

        return this.connection.prepareStatement(updateQuery).execute();
    }

    private E getPOJO(PreparedStatement statement, Class<E> table)
            throws SQLException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {

        final ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        final E entity = table.getDeclaredConstructor().newInstance();

        fillEntity(table, resultSet, entity);

        return entity;

    }

    private Iterable<E> getPOJOs(PreparedStatement statement, Class<E> table)
            throws SQLException, NoSuchMethodException,
            InvocationTargetException, InstantiationException,
            IllegalAccessException {
        final ResultSet resultSet = statement.executeQuery();

        List<E> entities = new ArrayList<>();

        while (resultSet.next()) {
            final E entity = table.getDeclaredConstructor().newInstance();

            fillEntity(table, resultSet, entity);

            entities.add(entity);
        }
        return entities;
    }

    private void fillEntity(Class<E> table, ResultSet resultSet, E entity)
            throws SQLException, IllegalAccessException {
        final Field[] declaredFields = table.getDeclaredFields();

        for (Field field : declaredFields) {
            field.setAccessible(true);

            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }

            Class<?> fieldType = field.getType();

            String columnName = field.getAnnotation(Column.class).name();

            if (fieldType == Long.class || fieldType == long.class) {
                field.setLong(entity, resultSet.getLong(columnName));
            } else if (fieldType == Integer.class || fieldType == int.class) {
                field.setInt(entity, resultSet.getInt(columnName));
            } else if (fieldType == LocalDate.class) {
                field.set(entity, resultSet.getDate(columnName).toLocalDate());
            } else if (fieldType == String.class) {
                field.set(entity, resultSet.getString(columnName));
            }

        }
    }

    private Field getIdColumn(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException(ID_COLUM_MISSING_MESSAGE));
    }

    private String getTableName(Class<?> clazz) {
        final Entity[] annotationsByType = clazz.getAnnotationsByType(Entity.class);

        if (annotationsByType.length == 0) {
            throw new UnsupportedOperationException(CLASS_MUST_BE_ENTITY_MESSAGE);
        }

        return annotationsByType[0].name();
    }

    private String mapFieldsToGivenType(Field field, E entity) {
        field.setAccessible(true);

        Object o = null;

        try {
            o = field.get(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return o instanceof String || o instanceof LocalDate
                ? "'" + o + "'"
                : Objects.requireNonNull(o).toString();
    }

    private List<KeyValuePair> getKeyValuePairs(E entity) {
        final Class<?> clazz = entity.getClass();

        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class) && f.isAnnotationPresent(Column.class))
                .map(f -> new KeyValuePair(f.getAnnotationsByType(Column.class)[0].name(),
                        mapFieldsToGivenType(f, entity)))
                .collect(Collectors.toList());
    }

    private record KeyValuePair(String key, String value) {
    }
}




