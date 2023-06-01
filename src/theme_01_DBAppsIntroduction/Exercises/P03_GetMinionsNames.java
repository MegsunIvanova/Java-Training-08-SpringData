package theme_01_DBAppsIntroduction.Exercises;

import java.sql.*;
import java.util.Scanner;

public class P03_GetMinionsNames {
    private static String GET_MINIONS_NAME_AND_AGE_BY_VILLAIN_ID =
            "SELECT m.name, m.age" +
                    " FROM minions AS m" +
                    " JOIN minions_villains AS mv ON m.id = mv.minion_id" +
                    " WHERE mv.villain_id = ?";
    private static String GET_VILLAIN_NAME_BY_ID =
            "SELECT v.name FROM villains AS v WHERE v.id = ?";
    private static String NO_VILLAIN_FORMAT = "No villain with ID %d exists in the database.";
    private static String VILLAIN_FORMAT = "Villain: %s%n";
    private static String MINION_FORMAT = "%d. %s %d%n";

    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();

        final int villainId = new Scanner(System.in).nextInt();

        final PreparedStatement villainStatement = connection.prepareStatement(GET_VILLAIN_NAME_BY_ID);

        villainStatement.setInt(1, villainId);

        final ResultSet villainSet = villainStatement.executeQuery();

        if (!villainSet.next()) {
            System.out.printf(NO_VILLAIN_FORMAT, villainId);
            connection.close();
            return;
        }

        final String dbVillainName = villainSet.getString(Constants.COLUMN_LABEL_NAME);

        System.out.printf(VILLAIN_FORMAT, dbVillainName);

        final PreparedStatement minionsStatement = connection.prepareStatement(GET_MINIONS_NAME_AND_AGE_BY_VILLAIN_ID);

        minionsStatement.setInt(1, villainId);

        final ResultSet minionsSet = minionsStatement.executeQuery();

        for (int index = 1; minionsSet.next(); index++) {
            String dbMinionName = minionsSet.getString(Constants.COLUMN_LABEL_NAME);
            int dbMinionAge = minionsSet.getInt(Constants.COLUMN_LABEL_AGE);

            System.out.printf(MINION_FORMAT, index, dbMinionName, dbMinionAge);
        }

        connection.close();

    }
}
