package aplicatieBancara.Entitati.Client;

import aplicatieBancara.Config.DatabaseConfiguration;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositoryClient {


    private static RepositoryClient repositoryClient;

    private RepositoryClient(){}

    public static RepositoryClient getInstance() {
        if (repositoryClient == null)
            repositoryClient = new RepositoryClient();

        return repositoryClient;
    }

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS clienti" +
                "(idClient int PRIMARY KEY, nume varchar(20), prenume varchar(50), CNP varchar(30), dataNastere varchar(12), email varchar(30), telefon varchar(12))";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            Statement stmt = connection.createStatement();
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addClient(Client c) {
        String insertClientSql = "INSERT INTO clienti(idClient, nume, prenume, CNP, dataNastere, email, telefon) VALUES(?, ?, ?, ?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertClientSql);
            preparedStatement.setInt(1, c.getIdClient());
            preparedStatement.setString(2, c.getNume());
            preparedStatement.setString(3, c.getPrenume());
            preparedStatement.setString(4, c.getCNP());
            preparedStatement.setString(5, c.getDataNastere().toString());
            preparedStatement.setString(6, c.getEmail());
            preparedStatement.setString(7, c.getTelefon());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Client getClientById(int idClient) {
        String selectSql = "SELECT * FROM clienti WHERE idClient=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1, idClient);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToClient(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<String[]> getClienti() {
        List<String[]> matrice = new ArrayList<>();
        String selectSql = "SELECT * FROM clienti";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                String[] linie = {Integer.toString(resultSet.getInt(1)), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7)};
                matrice.add(linie);
            }
            return matrice;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateEmail(String email, int idClient) {
        String updateEmailSql = "UPDATE clienti SET email=? WHERE idClient=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateEmailSql);
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, idClient);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateTelefon(String telefon, int idClient) {
        String updateTelefonSql = "UPDATE clienti SET telefon=? WHERE idClient=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateTelefonSql);
            preparedStatement.setString(1, telefon);
            preparedStatement.setInt(2, idClient);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteClient(int idClient) {
        String deleteClientSql = "DELETE FROM clienti WHERE idClient=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteClientSql);
            preparedStatement.setInt(1, idClient);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Client mapToClient(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            RepositoryAdresa repAdr = RepositoryAdresa.getInstance();
            int idClient = resultSet.getInt(1);
            Adresa adresaClient = repAdr.getAdresaByIdClient(idClient);
            return new Client(idClient, resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), LocalDate.parse(resultSet.getString(5)),
                    resultSet.getString(6), resultSet.getString(7), adresaClient);
        }

        return null;
    }
}
