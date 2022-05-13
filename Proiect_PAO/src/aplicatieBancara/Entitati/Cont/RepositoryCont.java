package aplicatieBancara.Entitati.Cont;

import aplicatieBancara.Config.DatabaseConfiguration;
import aplicatieBancara.Entitati.Banca;
import aplicatieBancara.Entitati.Client.Adresa;
import aplicatieBancara.Entitati.Client.Client;
import aplicatieBancara.Entitati.Client.RepositoryClient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositoryCont {

    private static RepositoryCont repositoryCont;

    private RepositoryCont(){}

    public static RepositoryCont getInstance() {
        if (repositoryCont == null)
            repositoryCont = new RepositoryCont();

        return repositoryCont;
    }

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS conturi" +
                "(idClient int, numeTitular varchar(100), IBAN varchar(30) PRIMARY KEY, sold double)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            Statement stmt = connection.createStatement();
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCont(Cont c) {
        String insertContSql = "INSERT INTO conturi(idClient, numeTitular, IBAN, sold) VALUES(?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertContSql);
            preparedStatement.setInt(1, c.getIdClient());
            preparedStatement.setString(2, c.getNumeTitular());
            preparedStatement.setString(3, c.getIBAN());
            preparedStatement.setDouble(4, c.getSold());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cont getContByIBAN(String IBAN) {
        String selectSql = "SELECT * FROM conturi WHERE IBAN=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setString(1, IBAN);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToCont(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<String[]> getConturi() {
        List<String[]> matrice = new ArrayList<>();
        String selectSql = "SELECT * FROM conturi";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                String[] linie = {Integer.toString(resultSet.getInt(1)), resultSet.getString(2), resultSet.getString(3), Double.toString(resultSet.getDouble(4))};
                matrice.add(linie);
            }
            return matrice;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateSold(Double sold, String IBAN) {
        String updateSoldSql = "UPDATE conturi SET sold=? WHERE IBAN=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSoldSql);
            preparedStatement.setDouble(1, sold);
            preparedStatement.setString(2, IBAN);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteCont(String IBAN) {
        String deleteContSql = "DELETE FROM conturi WHERE IBAN=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteContSql);
            preparedStatement.setString(1, IBAN);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Cont mapToCont(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Cont(resultSet.getString(3), resultSet.getString(2), resultSet.getInt(1), Banca.getBanca());
        }

        return null;
    }


}
