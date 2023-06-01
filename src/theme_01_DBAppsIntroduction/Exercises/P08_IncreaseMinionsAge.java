package theme_01_DBAppsIntroduction.Exercises;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class P08_IncreaseMinionsAge {
    private static final String UPDATE_MINIONS_NAMES_AND_AGE_BY_ID =
        "UPDATE minions AS m SET m.name = LOWER(m.name), m.age = m.age + 1 WHERE m.id IN (?)";
    private static final String GET_ALL_MINIONS = "SELECT m.id, m.name, m.age FROM minions AS m";
    private static final String TEXT_TO_REPLACE = "(?)";
    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        String minionsIds = Arrays.stream(new Scanner(System.in)
                .nextLine()
                .split("\\s+"))
                .collect(Collectors.joining(",", "(", ")"));


        final PreparedStatement updateMinionsStatement =
                connection.prepareStatement(UPDATE_MINIONS_NAMES_AND_AGE_BY_ID.replace(TEXT_TO_REPLACE, minionsIds));
        updateMinionsStatement.executeUpdate();


        final PreparedStatement getAllMinionsStatement = connection.prepareStatement(GET_ALL_MINIONS);
        final ResultSet allMinionsSet = getAllMinionsStatement.executeQuery();

        while (allMinionsSet.next()) {
            String name = allMinionsSet.getString(Constants.COLUMN_LABEL_NAME);
            int age = allMinionsSet.getInt(Constants.COLUMN_LABEL_AGE);
            System.out.printf(Constants.MINION_FORMAT, name, age);
        }

        connection.close();
    }
}
