package theme_01_DBAppsIntroduction.Exercises;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class P04_AddMinion {
    private static final String GET_TOWN_ID_BY_NAME = "SELECT t.id FROM towns AS t WHERE t.name = ?";
    private static final String GET_VILLAIN_ID_BY_NAME = "SELECT v.id FROM villains AS v WHERE v.name = ?";
    private static final String GET_LAST_MINION_ID = "SELECT m.id FROM minions AS m ORDER BY m.id DESC LIMIT 1";
    private static final String INSERT_INTO_TOWNS = "INSERT INTO towns(name) VALUES(?)";
    private static final String INSERT_INTO_VILLAINS = "INSERT INTO villains(name, evilness_factor) VALUES(?, ?)";
    private static final String INSERT_INTO_MINIONS = "INSERT INTO minions(name, age, town_id) VALUES(?, ?, ?)";
    private static final String INSERT_INTO_MINIONS_VILLAINS =
            "INSERT INTO minions_villains(minion_id, villain_id) VALUES(?, ?)";
    private static final String EVILNESS_FACTOR = "evil";
    private static final String TOWN_ADDED_FORMAT = "Town %s was added to the database.%n";
    private static final String VILLAIN_ADDED_FORMAT = "Villain %s was added to the database.%n";
    private static final String MINION_ADDED_FORMAT = "Successfully added %s to be minion of %s.%n";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        final Scanner scanner = new Scanner(System.in);
        final String[] minionData = scanner.nextLine().split("\\s+");

        final String minionName = minionData[1];
        final int minionAge = Integer.parseInt(minionData[2]);
        final String minionTown = minionData[3];

        final String villainName = scanner.nextLine().split("\\s+")[1];

        int dbTownId = getId(connection,
                List.of(minionTown),
                GET_TOWN_ID_BY_NAME,
                INSERT_INTO_TOWNS,
                TOWN_ADDED_FORMAT);

        int dbVillainId = getId(connection,
                List.of(villainName, EVILNESS_FACTOR),
                GET_VILLAIN_ID_BY_NAME,
                INSERT_INTO_VILLAINS,
                VILLAIN_ADDED_FORMAT);

        final PreparedStatement insertMinionStatement = connection.prepareStatement(INSERT_INTO_MINIONS);
        insertMinionStatement.setString(1, minionName);
        insertMinionStatement.setInt(2, minionAge);
        insertMinionStatement.setInt(3, dbTownId);

        insertMinionStatement.executeUpdate();

        final PreparedStatement selectLastMinionStatement = connection.prepareStatement(GET_LAST_MINION_ID);
        final ResultSet lastMinionSet = selectLastMinionStatement.executeQuery();
        lastMinionSet.next();

       final int dbMinionId = lastMinionSet.getInt(Constants.COLUMN_LABEL_ID);

        final PreparedStatement insertMinionVillainsStatement = connection.prepareStatement(INSERT_INTO_MINIONS_VILLAINS);
        insertMinionVillainsStatement.setInt(1, dbMinionId);
        insertMinionVillainsStatement.setInt(2, dbVillainId);
        insertMinionVillainsStatement.executeUpdate();

        System.out.printf(MINION_ADDED_FORMAT, minionName, villainName);

        connection.close();
    }

    private static int getId(Connection connection,
                             List<String> arguments,
                             String selectQuery,
                             String insertQuery,
                             String printFormat) throws SQLException {

        final String name = arguments.get(0);

        final PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        selectStatement.setString(1, name);

        final ResultSet resultSet = selectStatement.executeQuery();

        if (!resultSet.next()) {
            final PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

            for (int index = 1; index <= arguments.size(); index++) {
                insertStatement.setString(index, arguments.get(index - 1));
            }

            insertStatement.executeUpdate();

            final ResultSet newResultSet = selectStatement.executeQuery();
            newResultSet.next();

            System.out.printf(printFormat, name);

            return newResultSet.getInt(Constants.COLUMN_LABEL_ID);
        }

        return resultSet.getInt(Constants.COLUMN_LABEL_ID);
    }
}
