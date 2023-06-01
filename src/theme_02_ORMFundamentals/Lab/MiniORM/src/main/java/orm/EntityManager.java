package orm;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManager<E> implements DbContext<E> {
    private final Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws SQLException, IllegalAccessException, NoSuchFieldException {
        Long id = this.getIdentity(entity);
        String tableName = this.getTableName(entity.getClass());
        List<String> fieldList = this.getDBFieldsWithoutIdentity(entity);
        List<String> valueList = this.getValuesWithoutIdentity(entity);

        if (id == null) {
            return doInsert(tableName, fieldList, valueList);
        } else {
            return doUpdate(tableName, fieldList, valueList, id);
        }
    }

    @Override
    public Iterable<E> find(Class<E> entityType) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return find(entityType, null);
    }

    @Override
    public Iterable<E> find(Class<E> entityType, String where)
            throws SQLException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {

        String tableName = this.getTableName(entityType);

        String sql = String.format("SELECT * FROM %s %s",
                tableName, where == null ? "" : "WHERE " + where);


        ResultSet resultSet = this.connection.prepareStatement(sql).executeQuery();
        List<E> result = new ArrayList<>();

        E entity = this.createEntity(entityType, resultSet);

        while (entity != null) {
            result.add(entity);
            entity = this.createEntity(entityType, resultSet);
        }

        return result;

    }

    @Override
    public E findFirst(Class<E> entityType)
            throws SQLException, InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        return findFirst(entityType, null);
    }

    @Override
    public E findFirst(Class<E> entityType, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String tableName = this.getTableName(entityType);

        String sql = String.format("SELECT * FROM %s %s LIMIT 1",
                tableName, where == null ? "" : "WHERE " + where);

        ResultSet resultSet = this.connection.prepareStatement(sql).executeQuery();

        return this.createEntity(entityType, resultSet);
    }

    private boolean doInsert(String tableName, List<String> fieldList, List<String> valueList)
            throws SQLException {

        String fields = String.join(", ", fieldList);
        String values = String.join(", ", valueList);

        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)",
                tableName, fields, values);

        return this.connection.prepareStatement(sql).execute();
    }

    private boolean doUpdate(String tableName, List<String> fieldList, List<String> valueList, Long id) throws SQLException {

        List<String> argumentsForUpdate = new ArrayList<>();
        for (int i = 0; i < fieldList.size(); i++) {
            argumentsForUpdate.add(String.format("%s = %s", fieldList.get(i), valueList.get(i)));
        }

        String sql = String.format("UPDATE %s SET %s WHERE id = %d",
                tableName,
                String.join(", ", argumentsForUpdate),
                id);

        return this.connection.prepareStatement(sql).executeUpdate() == 1;
    }

    private Long getIdentity(E entity) throws IllegalAccessException {
        Field idField = Arrays.stream(entity
                        .getClass()
                        .getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElse(null);

        if (idField == null) {
            return null;
        }

        idField.setAccessible(true);

        long id = idField.getLong(entity);

        return (id > 0) ? id : null;
    }

    private String getTableName(Class<?> clazz) {
        Entity annotation = clazz.getAnnotation(Entity.class);

        if (annotation == null) {
            throw new ORMException("Provided class does not have Entity annotation");
        }

        return annotation.name();
    }

    private List<String> getDBFieldsWithoutIdentity(E entity) {
        return Arrays.stream(entity
                        .getClass()
                        .getDeclaredFields())
                .filter(f -> f.getAnnotation(Column.class) != null)
                .map(f -> f.getAnnotation(Column.class).name())
                .collect(Collectors.toList());

    }


    private List<String> getValuesWithoutIdentity(E entity) throws IllegalAccessException {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        List<String> result = new ArrayList<>();

        for (Field declaredField : declaredFields) {
            if (declaredField.getAnnotation(Column.class) == null) {
                continue;
            }

            declaredField.setAccessible(true);
            Object value = declaredField.get(entity);
            result.add("\"" + value.toString() + "\"");
        }

        return result;
    }

    private E createEntity(Class<E> entityType, ResultSet resultSet)
            throws SQLException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        if (!resultSet.next()) {
            return null;
        }

        E entity = entityType.getDeclaredConstructor().newInstance();

        Field[] declaredFields = entityType.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (!declaredField.isAnnotationPresent(Column.class) &&
                    !declaredField.isAnnotationPresent(Id.class)) {
                continue;
            }

            Column columnAnnotation = declaredField.getAnnotation(Column.class);

            String fieldName = columnAnnotation == null ?
                    declaredField.getName() : columnAnnotation.name();

            String value = resultSet.getString(fieldName);
            entity = this.fillData(entity, declaredField, value);
        }

        return entity;
    }

    private E fillData(E entity, Field field, String value) throws IllegalAccessException {
        field.setAccessible(true);

        if (field.getType() == long.class || field.getType() == Long.class) {
            field.setLong(entity, Long.parseLong(value));
        } else if (field.getType() == int.class || field.getType() == Integer.class) {
            field.setInt(entity, Integer.parseInt(value));
        } else if (field.getType() == LocalDate.class) {
            field.set(entity, LocalDate.parse(value));
        } else if (field.getType() == String.class) {
            field.set(entity, value);
        } else {
            throw new ORMException("Unsupported type " + field.getType());
        }

        return entity;

    }
}
