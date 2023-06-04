package theme_01_DBAppsIntroduction.Exercises;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class P08_IncreaseMinionsAge_v02 {

    private static final String UPDATE_MINIONS_NAMES_AND_AGE_BY_ID =
        "UPDATE minions AS m SET m.name = LOWER(m.name), m.age = m.age + 1 WHERE m.id IN (%s)";
    private static final String GET_ALL_MINIONS = "SELECT m.id, m.name, m.age FROM minions AS m";
    private static final String TEXT_TO_REPLACE = "(?)";

    public static void main(String[] args) throws SQLException {

        final Connection connection = Utils.getSQLConnection();

        final int [] minionsIds = Arrays.stream(new Scanner(System.in)
                .nextLine()
                .split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        StringBuilder paramsNumber = new StringBuilder();
        for (int minionId : minionsIds) {
            paramsNumber.append("?,");
        }

        paramsNumber.deleteCharAt(paramsNumber.length()-1);

       final String updateQuery = String.format(UPDATE_MINIONS_NAMES_AND_AGE_BY_ID, paramsNumber);

        final PreparedStatement updateMinionsStatement =
                connection.prepareStatement(updateQuery);
        for (int i = 1; i <= minionsIds.length ; i++) {
            updateMinionsStatement.setInt(i, minionsIds[i-1]);
        }
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
