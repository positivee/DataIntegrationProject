package integracja;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseHelper {
    private static final String user = "root";
    private static final String password = "";
    private static Connection connection = null;
    private final String url = "jdbc:mysql://localhost:3306/integracja?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    ResultSet resultSet;
    private Statement statement = null;
    private int countNewRecords;
    private int countDuplicatedNewRecords;

    public DatabaseHelper() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int getCountNewRecords() {
        return countNewRecords;
    }

    public void setCountNewRecords(int countNewRecords) {
        this.countNewRecords = countNewRecords;
    }

    public int getCountDuplicatedNewRecords() {
        return countDuplicatedNewRecords;
    }

    public void setCountDuplicatedNewRecords(int countDuplicatedNewRecords) {
        this.countDuplicatedNewRecords = countDuplicatedNewRecords;
    }

    public void createTableIfNotExist() throws SQLException {
        String tableCreation = "CREATE TABLE IF NOT EXISTS KATALOG " +
                "(id INTEGER NOT NULL AUTO_INCREMENT, " +
                " manufacturer VARCHAR(255), " +
                " size VARCHAR(255), " +
                " resolution VARCHAR(255), " +
                " type VARCHAR(255), " +
                " touch VARCHAR(10), " +
                " name VARCHAR(255), " +
                " physical_cores INTEGER, " +
                " clock_speed INTEGER, " +
                " ram VARCHAR(10), " +
                " storage VARCHAR(20), " +
                " disc_type VARCHAR(10), " +
                " graphic_card_name VARCHAR(255), " +
                " memory VARCHAR(20), " +
                " os VARCHAR(255), " +
                " disc_reader VARCHAR(255), " +
                " PRIMARY KEY ( id ))";
        statement.executeUpdate(tableCreation);
    }

    public void saveTableToDatabase(JTable table, Set<Integer> duplicateRowSet, Set<Integer> modifyRowSet) throws SQLException {
        createTableIfNotExist();
        String deleteSql = "DELETE FROM katalog;";
        statement.executeUpdate(deleteSql);

        String[] currentRecord = new String[15];
        List<String[]> listSaveChecker = new ArrayList<>();
        String sql = "INSERT INTO katalog ( manufacturer, size, resolution, type, touch, name, physical_cores, clock_speed, ram, storage, disc_type, graphic_card_name, memory, os, disc_reader) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int row = 0; row < table.getRowCount(); row++) {
            currentRecord = new String[15];
            for (int column = 0; column < table.getColumnCount(); column++) {
                currentRecord[column] = String.valueOf(table.getModel().getValueAt(row, column));
            }
            if ((!modifyRowSet.contains(row) && !duplicateRowSet.contains(row)) || (modifyRowSet.contains(row) && !App.isRecordDuplicated(listSaveChecker, currentRecord))) {

                preparedStatement.setString(1, String.valueOf(table.getModel().getValueAt(row, 0))); //manufacturer
                preparedStatement.setString(2, String.valueOf(table.getModel().getValueAt(row, 1)));  //size
                preparedStatement.setString(3, String.valueOf(table.getModel().getValueAt(row, 2)));
                preparedStatement.setString(4, String.valueOf(table.getModel().getValueAt(row, 3)));
                preparedStatement.setString(5, String.valueOf(table.getModel().getValueAt(row, 4)));
                preparedStatement.setString(6, String.valueOf(table.getModel().getValueAt(row, 5)));
                preparedStatement.setInt(7, checkInteger(String.valueOf(table.getModel().getValueAt(row, 6))));
                preparedStatement.setInt(8, checkInteger(String.valueOf(table.getModel().getValueAt(row, 7))));
                preparedStatement.setString(9, String.valueOf(table.getModel().getValueAt(row, 8)));
                preparedStatement.setString(10, String.valueOf(table.getModel().getValueAt(row, 9)));
                preparedStatement.setString(11, String.valueOf(table.getModel().getValueAt(row, 10)));
                preparedStatement.setString(12, String.valueOf(table.getModel().getValueAt(row, 11)));
                preparedStatement.setString(13, String.valueOf(table.getModel().getValueAt(row, 12)));
                preparedStatement.setString(14, String.valueOf(table.getModel().getValueAt(row, 13)));
                preparedStatement.setString(15, String.valueOf(table.getModel().getValueAt(row, 14)));

                preparedStatement.executeUpdate();
                listSaveChecker.add(currentRecord);
            }
        }
    }

    public void saveDatabaseToList(List<String[]> list, Set<Integer> duplicateRowSet) throws SQLException {
        String physical_cores = null;
        String clock_speed = null;
        createTableIfNotExist();
        String sql = "SELECT * FROM KATALOG";
        ResultSet resultSet = statement.executeQuery(sql);


        countDuplicatedNewRecords = 0;
        countNewRecords = 0;
        while (resultSet.next()) {
            String[] linia = new String[16];
            if (resultSet.getInt("physical_cores") == 0) {
                physical_cores = "";
            } else {
                physical_cores = String.valueOf(resultSet.getInt("physical_cores"));
            }
            if (resultSet.getInt("clock_speed") == 0) {
                clock_speed = "";
            } else {
                clock_speed = String.valueOf(resultSet.getInt("clock_speed"));
            }
            linia[0] = resultSet.getString("manufacturer");
            linia[1] = resultSet.getString("size");
            linia[2] = resultSet.getString("resolution");
            linia[3] = resultSet.getString("type");
            linia[4] = resultSet.getString("touch");
            linia[5] = resultSet.getString("name");
            linia[6] = physical_cores;
            linia[7] = clock_speed;
            linia[8] = resultSet.getString("ram");
            linia[9] = resultSet.getString("storage");
            linia[10] = resultSet.getString("disc_type");
            linia[11] = resultSet.getString("graphic_card_name");
            linia[12] = resultSet.getString("memory");
            linia[13] = resultSet.getString("os");
            linia[14] = resultSet.getString("disc_reader");
            linia[15] = "";

            if (App.isRecordDuplicated(list, linia)) {
                duplicateRowSet.add(list.size());
                countDuplicatedNewRecords++;


            }
            ;
            list.add(linia);
            countNewRecords++;
        }

        setCountDuplicatedNewRecords(countDuplicatedNewRecords);
        setCountNewRecords(countNewRecords);

    }


    public Integer checkInteger(String testString) {
        try {
            Integer.parseInt(testString);
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        }
        return Integer.parseInt(testString);
    }

    public Integer getCountOfManufacturer(String manufacturer) {
        int quantity = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from katalog where manufacturer like ?");
            preparedStatement.setString(1, manufacturer);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            quantity = resultSet.getInt(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return quantity;
    }


    public Integer getCountOfResolution(String resolution) {
        int resolutionCount = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from katalog where resolution like ?");
            preparedStatement.setString(1, resolution);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            resolutionCount = resultSet.getInt(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resolutionCount;
    }


    //    public List<String> prepopulateResolutionList() throws SQLException {
//        String sql;
//        List<String> items = new ArrayList<>();
//
//        sql = "select distinct(resolution) from KATALOG";
//        String sql = "SELECT * FROM KATALOG";
//        ResultSet resultSet = statement.executeQuery(sql);
//       // items = getElementArrayFromQuery(arraySize);
//        return items;
//    }
    public List<String> distinctList(String makeDistinct) throws SQLException {
        String sql;
        List<String> items = new ArrayList<>();

        sql = "select distinct(" + makeDistinct + ") from katalog";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            if (!resultSet.getString(makeDistinct).isEmpty())
                items.add(resultSet.getString(makeDistinct));
        }
        return items;
    }

    public List<String[]> getListOfelementValue(String columnName, String value) throws SQLException {
        String sql;
        String physical_cores = null;
        String clock_speed = null;
        String linia[] = new String[16];
        List<String[]> items = new ArrayList<>();
        sql = "select * from katalog where " + columnName + " like '" + value + "'";

        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            linia = new String[16];
            if (resultSet.getInt("physical_cores") == 0) {
                physical_cores = "";
            } else {
                physical_cores = String.valueOf(resultSet.getInt("physical_cores"));
            }
            if (resultSet.getInt("clock_speed") == 0) {
                clock_speed = "";
            } else {
                clock_speed = String.valueOf(resultSet.getInt("clock_speed"));
            }
            linia[0] = resultSet.getString("manufacturer");
            linia[1] = resultSet.getString("size");
            linia[2] = resultSet.getString("resolution");
            linia[3] = resultSet.getString("type");
            linia[4] = resultSet.getString("touch");
            linia[5] = resultSet.getString("name");
            linia[6] = physical_cores;
            linia[7] = clock_speed;
            linia[8] = resultSet.getString("ram");
            linia[9] = resultSet.getString("storage");
            linia[10] = resultSet.getString("disc_type");
            linia[11] = resultSet.getString("graphic_card_name");
            linia[12] = resultSet.getString("memory");
            linia[13] = resultSet.getString("os");
            linia[14] = resultSet.getString("disc_reader");


            items.add(linia);
        }
        return items;
    }

}