package aplicatieBancara.Entitati.Client;

import java.time.format.DateTimeFormatter;
import java.time.*;
import java.util.Objects;

public class Client {

//    private static int id = 0;
    private final int idClient;
    private String nume;
    private String prenume;
    private final String CNP;
    private final LocalDate  dataNastere;
    private String email;
    private String telefon;
    private Adresa adresa;

    public Client(int id, String nume, String prenume, String CNP, LocalDate  dataNastere, String email, String telefon, Adresa adresa) {
//        id  = id + 1;
        this.idClient = id;
        this.nume = nume;
        this.prenume = prenume;
        this.CNP = CNP;
        this.dataNastere = dataNastere;
        this.email = email;
        this.telefon = telefon;
        this.adresa = adresa;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-M-yyyy");
        return "ID client: " + idClient + "\n" +
                "Nume: " + nume + "\n" +
                "Prenume: " + prenume + "\n" +
                "CNP: " + CNP + "\n" +
                //"Data nasterii: " + dataNastere.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() + "\n" +
                "Data nasterii: " + dataNastere.format(formatter) + "\n" +
                "Email: " + email + "\n" +
                "Telefon: " + telefon + "\n" +
                "Adresa:\n" + adresa + "\n";
    }


    public int getIdClient() {
        return idClient;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getCNP() {
        return CNP;
    }


    public LocalDate  getDataNastere() {
        return dataNastere;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public String toCSV(){
        return this.idClient + "," + this.nume + "," + this.prenume + "," + this.CNP + "," + this.dataNastere + "," + this.email + "," + this.telefon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return idClient == client.idClient && nume.equals(client.nume) && prenume.equals(client.prenume) && CNP.equals(client.CNP) && dataNastere.equals(client.dataNastere) && email.equals(client.email) && telefon.equals(client.telefon) && adresa.equals(client.adresa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient, nume, prenume, CNP, dataNastere, email, telefon, adresa);
    }
}
