package aplicatieBancara.Servicii;

import aplicatieBancara.Config.DatabaseConfiguration;
import aplicatieBancara.Entitati.RepositoryTranzactie;
import aplicatieBancara.Entitati.Tranzactie;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RepositoryAudit {

    final DateTimeFormatter formatter;
    private static RepositoryAudit repositoryAudit;

    private RepositoryAudit(){
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    public static RepositoryAudit getInstance() {
        if (repositoryAudit == null)
            repositoryAudit = new RepositoryAudit();

        return repositoryAudit;
    }

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS audit" +
                "(id int PRIMARY KEY AUTO_INCREMENT, actiune varchar(100), moment varchar(20))";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            Statement stmt = connection.createStatement();
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addActiune(String actiune) {
        String insertActiuneSql = "INSERT INTO audit(actiune, moment) VALUES(?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertActiuneSql);
            String moment = formatter.format(LocalDateTime.now());
            preparedStatement.setString(1, actiune);
            preparedStatement.setString(2, moment);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getActiuneById(int id) {
        String selectSql = "SELECT * FROM audit WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getString(1) + "," + resultSet.getString(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
