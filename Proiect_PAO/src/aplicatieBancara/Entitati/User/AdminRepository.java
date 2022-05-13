package aplicatieBancara.Entitati.User;

import aplicatieBancara.Config.DatabaseConfiguration;
import aplicatieBancara.Entitati.Client.Adresa;
import aplicatieBancara.Entitati.Client.Client;
import aplicatieBancara.Entitati.Client.RepositoryAdresa;
import aplicatieBancara.Entitati.Cont.RepositoryCont;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdminRepository {


    private static AdminRepository adminRepository;

    private AdminRepository(){}

    public static AdminRepository getInstance() {
        if (adminRepository == null)
            adminRepository = new AdminRepository();

        return adminRepository;
    }

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS admins" +
                "(id int PRIMARY KEY, username varchar(30), password varchar(30))";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            Statement stmt = connection.createStatement();
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAdmin(Admin admin) {
        int id = admin.getId();
        String username = admin.getUsername();
        String password = admin.getParola();

        String insertAdminSql = "INSERT INTO admins(id, username, password) VALUES(?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertAdminSql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate(); // returns no of altered lines
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Admin getAdminById(int id) {
        String selectSql = "SELECT * FROM admins WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToAdmin(resultSet);
        } catch (SQLException | UsernameException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<String[]> getAllAdmins() {
        List<String[]> matrice = new ArrayList<>();
        String selectSql = "SELECT * FROM admins";

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

    public void updatePassword(String password, int id) {
        String updatePasswordSql = "UPDATE admins SET password=? WHERE id=?";

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

    public void deleteAdmin(int id) {
        String deleteAdminSql = "DELETE FROM admins WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteAdminSql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Admin mapToAdmin(ResultSet resultSet) throws SQLException, UsernameException {
        if (resultSet.next()) {
            return new Admin(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
        }

        return null;
    }

}
