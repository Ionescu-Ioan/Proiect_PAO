package aplicatieBancara.Entitati.Client;

import aplicatieBancara.Config.DatabaseConfiguration;
import aplicatieBancara.Entitati.Card.RepositoryCard;
import aplicatieBancara.Entitati.User.User;
import aplicatieBancara.Entitati.User.UsernameException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositoryAdresa {


    private static RepositoryAdresa repositoryAdresa;

    private RepositoryAdresa(){}

    public static RepositoryAdresa getInstance() {
        if (repositoryAdresa == null)
            repositoryAdresa = new RepositoryAdresa();

        return repositoryAdresa;
    }


    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS adrese" +
                "(idClient int PRIMARY KEY, strada varchar(30), oras varchar(50), tara varchar(30), codPostal varchar(10))";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            Statement stmt = connection.createStatement();
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAdresa(Adresa adresa) {
        String insertAdresaSql = "INSERT INTO adrese(idClient, strada, oras, tara, codPostal) VALUES(?, ?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertAdresaSql);
            preparedStatement.setInt(1, adresa.getIdClient());
            preparedStatement.setString(2, adresa.getStrada());
            preparedStatement.setString(3, adresa.getOras());
            preparedStatement.setString(4, adresa.getTara());
            preparedStatement.setString(5, adresa.getCodPostal());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Adresa getAdresaByIdClient(int idClient) {
        String selectSql = "SELECT * FROM adrese WHERE idClient=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1, idClient);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToAdresa(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<String[]> getAdrese() {
        List<String[]> matrice = new ArrayList<>();
        String selectSql = "SELECT * FROM adrese";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                String[] linie = {Integer.toString(resultSet.getInt(1)), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)};
                matrice.add(linie);
            }
            return matrice;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void updateAdresa(String strada, String oras, String tara, String codPostal, int idClient) {
        String updateAdresaSql = "UPDATE adrese SET strada=?, oras=?, tara=?, codPostal=? WHERE idClient=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateAdresaSql);
            preparedStatement.setString(1, strada);
            preparedStatement.setString(2, oras);
            preparedStatement.setString(3, tara);
            preparedStatement.setString(4, codPostal);
            preparedStatement.setInt(5, idClient);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteAdresa(int idClient) {
        String deleteAdresaSql = "DELETE FROM adrese WHERE idClient=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteAdresaSql);
            preparedStatement.setInt(1, idClient);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Adresa mapToAdresa(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Adresa a = new Adresa(resultSet.getString(2), resultSet.getString(3),resultSet.getString(4), resultSet.getString(5));
            a.setIdClient(resultSet.getInt(1));
            return a;
        }

        return null;
    }

}
