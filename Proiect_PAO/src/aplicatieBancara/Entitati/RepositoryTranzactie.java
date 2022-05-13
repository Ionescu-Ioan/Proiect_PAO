package aplicatieBancara.Entitati;

import aplicatieBancara.Config.DatabaseConfiguration;
import aplicatieBancara.Entitati.Card.Card;
import aplicatieBancara.Entitati.Card.CardCredit;
import aplicatieBancara.Entitati.Card.CardDebit;
import aplicatieBancara.Entitati.Cont.Cont;
import aplicatieBancara.Entitati.Cont.RepositoryCont;
import aplicatieBancara.Entitati.User.UserRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositoryTranzactie {

    private static RepositoryTranzactie repositoryTranzactie;

    private RepositoryTranzactie(){}

    public static RepositoryTranzactie getInstance() {
        if (repositoryTranzactie == null)
            repositoryTranzactie = new RepositoryTranzactie();

        return repositoryTranzactie;
    }

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS tranzactii" +
                "(id int PRIMARY KEY AUTO_INCREMENT, IBANSursa varchar(30), IBANDestinatie varchar(30), suma double, descriere varchar(60), tip varchar(10), data varchar(12))";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            Statement stmt = connection.createStatement();
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTranzactie(Tranzactie t) {
        String insertTranzactieSql = "INSERT INTO tranzactii(IBANSursa, IBANDestinatie, suma, descriere, tip, data) VALUES(?, ?, ?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertTranzactieSql);
            preparedStatement.setString(1, t.getIBANSursa());
            preparedStatement.setString(2, t.getIBANDestinatie());
            preparedStatement.setDouble(3, t.getSuma());
            preparedStatement.setString(4, t.getDescriere());
            preparedStatement.setString(5, t.getTip());
            preparedStatement.setString(6, t.getData().toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Tranzactie getTranzactieById(int id) {
        String selectSql = "SELECT * FROM tranzactii WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToTranzactie(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<String[]> getTranzactii() {
        List<String[]> matrice = new ArrayList<>();
        String selectSql = "SELECT * FROM tranzactii";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                String[] linie = {Integer.toString(resultSet.getInt(1)), resultSet.getString(2), resultSet.getString(3), Double.toString(resultSet.getDouble(4)),
                        resultSet.getString(5),  resultSet.getString(6),  resultSet.getString(7)};
                matrice.add(linie);
            }
            return matrice;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateDescriere(String descriere, int id) {
        String updateDescriereSql = "UPDATE tranzactii SET descriere=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateDescriereSql);
            preparedStatement.setString(1, descriere);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteTranzactie(int id) {
        String deleteTranzactieSql = "DELETE FROM tranzactii WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteTranzactieSql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Tranzactie mapToTranzactie(ResultSet resultSet) throws Exception {
        if (resultSet.next()) {
            TipTranzactie tip;
            if( resultSet.getString(5).equals("DEPUNERE"))
                tip = TipTranzactie.DEPUNERE;
            else if(resultSet.getString(5).equals("RETRAGERE"))
                tip = TipTranzactie.RETRAGERE;
            else
                tip = TipTranzactie.TRANSFER;
           return new Tranzactie(resultSet.getString(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getString(4), tip, LocalDate.parse(resultSet.getString(6)));
        }
        return null;
    }
}
