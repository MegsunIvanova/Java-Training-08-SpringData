package theme_01_DBAppsIntroduction.Exercises;

import java.sql.*;
import java.util.Scanner;

public class P09_IncreaseAgeStoredProcedure {
    private static final String INCREMENT_MINION_AGE_BY_ID = "CALL usp_get_older(?)";
    private static final String GET_MINION_BY_ID = "SELECT m.name, m.age FROM minions AS m WHERE m.id = ?";
    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();
        int minionId = Integer.parseInt(new Scanner(System.in).nextLine());

        CallableStatement incrementAgeStatement = connection.prepareCall(INCREMENT_MINION_AGE_BY_ID);
        incrementAgeStatement.setInt(1, minionId);
        incrementAgeStatement.executeQuery();


        PreparedStatement getMinionStatement = connection.prepareStatement(GET_MINION_BY_ID);
        getMinionStatement.setInt(1, minionId);
        ResultSet minionSet = getMinionStatement.executeQuery();
        minionSet.next();

        String name = minionSet.getString(Constants.COLUMN_LABEL_NAME);
        int age = minionSet.getInt(Constants.COLUMN_LABEL_AGE);

        System.out.printf(Constants.MINION_FORMAT, name, age);
       connection.close();
    }
}
