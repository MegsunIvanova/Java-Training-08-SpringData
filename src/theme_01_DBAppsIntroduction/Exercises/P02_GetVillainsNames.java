package theme_01_DBAppsIntroduction.Exercises;

import java.sql.*;

public class P02_GetVillainsNames {
    private static final String GET_VILLAINS_NAMES =
            "SELECT v.name, COUNT(DISTINCT mv.minion_id) AS minions_count " +
                    "FROM villains AS v " +
                    "JOIN minions_villains AS mv ON v.id = mv.villain_id " +
                    "GROUP BY mv.villain_id " +
                    "HAVING minions_count > ? " +
                    "ORDER BY minions_count";
    private static final String COLUMN_LABEL_MINIONS_COUNT = "minions_count";
    private static final String PRINT_FORMAT = "%s %d";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        final PreparedStatement statement = connection.prepareStatement(GET_VILLAINS_NAMES);

        statement.setInt(1, 15);

        final ResultSet result = statement.executeQuery();

        while (result.next()) {
            String dbVillainName = result.getString(Constants.COLUMN_LABEL_NAME);
            int dbMinionsCount = result.getInt(COLUMN_LABEL_MINIONS_COUNT);
            System.out.printf(PRINT_FORMAT, dbVillainName, dbMinionsCount);
        }

        connection.close();
    }
}
