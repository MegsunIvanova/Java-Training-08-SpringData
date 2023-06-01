package theme_01_DBAppsIntroduction.Exercises;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P06_RemoveVillain {
    private static final String GET_VILLAIN_BY_ID = "SELECT v.name FROM villains AS v WHERE v.id = ?";
    private static final String GET_MINIONS_COUNT_BY_VILLAIN_ID =
            "SELECT COUNT(mv.minion_id) AS minions_count FROM minions_villains AS mv WHERE mv.villain_id = ?";

    private static final String DELETE_MINIONS_VILLAINS_BY_VILLAIN_ID =
            "DELETE FROM minions_villains AS mv WHERE mv.villain_id = ?";
    private static final String DELETE_VILLAIN_BY_ID = "DELETE FROM villains AS v WHERE v.id = ?";

    private static final String NO_SUCH_VILLAIN_MESSAGE = "No such villain was found";
    private static final String DELETED_VILLAIN_FORMAT = "%s was deleted%n";
    private static final String DELETED_COUNT_OF_MINIONS_FORMAT = "%d minions released";
    private static final String COLUMN_LABEL_MINION_COUNT = "minions_count";


    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        final int villainId = new Scanner(System.in).nextInt();

        final PreparedStatement selectVillainStatement = connection.prepareStatement(GET_VILLAIN_BY_ID);
        selectVillainStatement.setInt(1, villainId);

        final ResultSet villainSet = selectVillainStatement.executeQuery();

        if (!villainSet.next()) {
            System.out.println(NO_SUCH_VILLAIN_MESSAGE);
            connection.close();
            return;
        }

        final String dbVillainName = villainSet.getString(Constants.COLUMN_LABEL_NAME);

        final PreparedStatement selectMinionsStatement = connection.prepareStatement(GET_MINIONS_COUNT_BY_VILLAIN_ID);
        selectMinionsStatement.setInt(1, villainId);

        final ResultSet countMinionsSet = selectMinionsStatement.executeQuery();
        countMinionsSet.next();

        final int countOfDeletedMinions = countMinionsSet.getInt(COLUMN_LABEL_MINION_COUNT);

        connection.setAutoCommit(false);

        try (
                PreparedStatement releaseMinionsStatement =
                        connection.prepareStatement(DELETE_MINIONS_VILLAINS_BY_VILLAIN_ID);

                PreparedStatement deleteVillainStatement =
                        connection.prepareStatement(DELETE_VILLAIN_BY_ID);) {

            releaseMinionsStatement.setInt(1, villainId);
            releaseMinionsStatement.executeUpdate();

            deleteVillainStatement.setInt(1, villainId);
            deleteVillainStatement.executeUpdate();

            connection.commit();
            System.out.printf(DELETED_VILLAIN_FORMAT, dbVillainName);
            System.out.printf(DELETED_COUNT_OF_MINIONS_FORMAT, countOfDeletedMinions);


        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }


        connection.close();
    }
}
