package aplicatieBancara.Entitati.User;

import aplicatieBancara.Config.DatabaseConfiguration;
import aplicatieBancara.Entitati.Cont.RepositoryCont;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static UserRepository userRepository;

    private UserRepository(){}

    public static UserRepository getInstance() {
        if (userRepository == null)
            userRepository = new UserRepository();

        return userRepository;
    }

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS users" +
                "(id int PRIMARY KEY, username varchar(30), password varchar(30))";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            Statement stmt = connection.createStatement();
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        String insertUserSql = "INSERT INTO users(id, username, password) VALUES(?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertUserSql);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getParola());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int id) {
        String selectSql = "SELECT * FROM users WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToUser(resultSet);
        } catch (SQLException | UsernameException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<String[]> getAllUsers() {
        List<String[]> matrice = new ArrayList<>();
        String selectSql = "SELECT * FROM users";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                String[] linie = {Integer.toString(resultSet.getInt(1)), resultSet.getString(2), resultSet.getString(3)};
                matrice.add(linie);
            }
            return matrice;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<Integer> getAllUsedIds()
    {
        List<Integer> lista = new ArrayList<>();
        String selectSql = "SELECT id FROM users";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                lista.add(resultSet.getInt(1));
            }
            return lista;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void updateUserName(String username, int id) {
        String updateUserNameSql = "UPDATE users SET username=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateUserNameSql);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updatePassword(String password, int id) {
        String updatePasswordSql = "UPDATE users SET password=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updatePasswordSql);
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteUser(int id) {
            String deleteUserSql = "DELETE FROM users WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteUserSql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User mapToUser(ResultSet resultSet) throws SQLException, UsernameException {
        if (resultSet.next()) {
            return new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
        }

        return null;
    }
}
