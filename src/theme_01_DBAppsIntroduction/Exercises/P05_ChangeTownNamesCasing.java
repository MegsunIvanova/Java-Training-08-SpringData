package theme_01_DBAppsIntroduction.Exercises;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class P05_ChangeTownNamesCasing {
    private static final String UPDATE_TOWN_NAME = "UPDATE towns AS t SET name = UPPER(name) WHERE country = ?";
    private static final String GET_ALL_TOWNS_NAMES_BY_COUNTRY = "SELECT t.name FROM towns AS t WHERE country = ?";
    private static final String NO_TOWNS_MESSAGE = "No town names were affected.";
    private static final String COUNT_OF_AFFECTED_TOWNS_FORMAT = "%d town names were affected.%n";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        final String country = new Scanner(System.in).nextLine();

        final PreparedStatement updateStatement = connection.prepareStatement(UPDATE_TOWN_NAME);
        updateStatement.setString(1, country);
        int updatedCount = updateStatement.executeUpdate();

        if (updatedCount == 0) {
            System.out.println(NO_TOWNS_MESSAGE);
            connection.close();
            return;
        }

        System.out.printf(COUNT_OF_AFFECTED_TOWNS_FORMAT, updatedCount);

        final PreparedStatement selectAllTownsStatement = connection.prepareStatement(GET_ALL_TOWNS_NAMES_BY_COUNTRY);
        selectAllTownsStatement.setString(1, country);
        final ResultSet townsSet = selectAllTownsStatement.executeQuery();

        ArrayList<String> towns = new ArrayList<>();

        while (townsSet.next()) {
            String town = townsSet.getString(Constants.COLUMN_LABEL_NAME);
            towns.add(town);
        }

        System.out.println(towns);

        connection.close();
    }
}
