package theme_01_DBAppsIntroduction.Exercises;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class P07_PrintAllMinionNames {
    private static final String GET_MINIONS_COUNT = "SELECT COUNT(*) AS 'count' FROM minions";
    private static final String GET_NAMES_OF_FIRST_HALF_MINIONS = "SELECT m.name FROM minions AS m LIMIT ?";
    private static final String GET_NAMES_OF_SECOND_HALF_MINIONS_REVERSED =
            "SELECT m.name FROM minions AS m ORDER BY m.id DESC LIMIT ?";
    private static final String COLUMN_LABEL_COUNT = "count";
    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        final PreparedStatement countStatement = connection.prepareStatement(GET_MINIONS_COUNT);
        final ResultSet countSet = countStatement.executeQuery();
        countSet.next();
        final int countMinions = countSet.getInt(COLUMN_LABEL_COUNT);

        final int rowsLimit = countMinions / 2;

        final PreparedStatement firstHalfMinionsStatement =
                connection.prepareStatement(GET_NAMES_OF_FIRST_HALF_MINIONS);
        firstHalfMinionsStatement.setInt(1, countMinions % 2 != 0 ? rowsLimit + 1 : rowsLimit);
        final ResultSet firstHalfMinionsSet = firstHalfMinionsStatement.executeQuery();

        final PreparedStatement secondHalfMinionsStatement =
                connection.prepareStatement(GET_NAMES_OF_SECOND_HALF_MINIONS_REVERSED);
        secondHalfMinionsStatement.setInt(1, rowsLimit);
        final ResultSet secondHalfMinionsSet = secondHalfMinionsStatement.executeQuery();

        while (secondHalfMinionsSet.next() && firstHalfMinionsSet.next()) {
            printMinionName(firstHalfMinionsSet);
            printMinionName(secondHalfMinionsSet);
        }

        if (firstHalfMinionsSet.next()) {
            printMinionName(firstHalfMinionsSet);
        }

        connection.close();
    }

    private static void printMinionName(ResultSet minionsResultSet) throws SQLException {
        final String minionName = minionsResultSet.getString(Constants.COLUMN_LABEL_NAME);
        System.out.println(minionName);
    }
}
