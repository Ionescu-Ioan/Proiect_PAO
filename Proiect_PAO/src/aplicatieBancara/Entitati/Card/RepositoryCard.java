package aplicatieBancara.Entitati.Card;

import aplicatieBancara.Config.DatabaseConfiguration;
import aplicatieBancara.Entitati.Banca;
import aplicatieBancara.Entitati.Cont.Cont;
import aplicatieBancara.Entitati.Cont.RepositoryCont;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositoryCard {

    private static RepositoryCard repositoryCard;

    private RepositoryCard(){}

    public static RepositoryCard getInstance() {
        if (repositoryCard == null)
            repositoryCard = new RepositoryCard();

        return repositoryCard;
    }

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS carduri" +
                "(cardId int PRIMARY KEY, IBAN varchar(30), tip varchar(10), sumaCreditata double, CVV int, numarCard varchar(30), data varchar(12))";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            Statement stmt = connection.createStatement();
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCard(Card c) {
        String insertCardSql = "INSERT INTO carduri(cardId, IBAN, tip, sumaCreditata, CVV, numarCard, data) VALUES(?, ?, ?, ?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertCardSql);
            preparedStatement.setInt(1, c.getCardId());
            preparedStatement.setString(2, c.getIBAN());
            preparedStatement.setString(3, c.getTip());
            preparedStatement.setInt(5, c.getCVV());
            preparedStatement.setString(6, c.getNumar());
            preparedStatement.setString(7, c.getDataExpirare().toString());
            if(c instanceof CardDebit)
                preparedStatement.setDouble(4, 0.0);
            else if(c instanceof CardCredit)
                preparedStatement.setDouble(4, ((CardCredit) c).getSumaCreditata());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Card getCardById(int cardId) {
        String selectSql = "SELECT * FROM carduri WHERE cardId=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1, cardId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToCard(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<String[]> getCarduri() {
        List<String[]> matrice = new ArrayList<>();
        String selectSql = "SELECT * FROM carduri";

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

    public void updateSumaCreditata(Double sumaCreditata, int cardId) {

        Card card = this.getCardById(cardId);

        if(card.getTip().equals("CREDIT")) {
            String updateSumaCreditataSql = "UPDATE carduri SET sumaCreditata=? WHERE cardId=?";

            Connection connection = DatabaseConfiguration.getDatabaseConnection();

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(updateSumaCreditataSql);
                preparedStatement.setDouble(1, sumaCreditata);
                preparedStatement.setInt(2, cardId);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteCard(int cardId) {
        String deleteCardSql = "DELETE FROM carduri WHERE cardId=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteCardSql);
            preparedStatement.setInt(1, cardId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Card mapToCard(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            RepositoryCont repCont = RepositoryCont.getInstance();
            Cont cont = repCont.getContByIBAN(resultSet.getString(2));
            if(resultSet.getString(3).equals("DEBIT"))
                return new CardDebit(resultSet.getInt(1), cont, resultSet.getInt(5), resultSet.getString(6), LocalDate.parse(resultSet.getString(7)));
            else if(resultSet.getString(3).equals("CREDIT"))
                return new CardCredit(resultSet.getInt(1), cont, resultSet.getInt(5), resultSet.getString(6), LocalDate.parse(resultSet.getString(7)), resultSet.getDouble(4));
        }

        return null;
    }
}
