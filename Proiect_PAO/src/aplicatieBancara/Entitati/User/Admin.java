package aplicatieBancara.Entitati.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Admin {

    private static List<String> listOfAdminNames = new ArrayList<>();

    private String username;
    private String parola;
    private final int id;

    public Admin(int id, String username, String parola) throws UsernameException{
        if(listOfAdminNames.contains(username))
        {
            throw new UsernameException("Username deja existent!");
        }
        else
        {
            this.username = username;
            listOfAdminNames.add(username);
            this.parola = parola;
            this.id = id;
        }
    }

    public String toCSV(){
        return this.id + "," + this.username + "," + this.parola;
    }

    public static List<String> getListOfAdminNames() {
        return listOfAdminNames;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", parola='" + parola + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return id == admin.id && username.equals(admin.username) && parola.equals(admin.parola);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, parola, id);
    }
}
