package aplicatieBancara.Entitati.User;

import aplicatieBancara.Entitati.Client.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private static List<String> listOfUserNames = new ArrayList<>();

    private final int idClient;
    private String username;
    private String parola;

    public User(int id, String username, String parola) throws UsernameException{

        if(listOfUserNames.contains(username))
        {
            throw new UsernameException("Username deja existent!");
        }
        else {
            this.idClient = id;
            this.username = username;
            listOfUserNames.add(username);
            this.parola = parola;
        }
    }

    public String toCSV(){
        return this.idClient + "," + this.username + "," + this.parola;
    }


    public static List<String> getListOfUserNames() {
        return listOfUserNames;
    }


    public int getId() {
        return idClient;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + idClient +
                ", username='" + username + '\'' +
                ", parola='" + parola + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() && getUsername().equals(user.getUsername()) && getParola().equals(user.getParola());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getParola());
    }
}
