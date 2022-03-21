package aplicatieBancara.Client;

import java.util.*;

public class Client {

    private final int idClient;
    private String nume;
    private String prenume;
    private String CNP;
    private Date dataNastere;
    private String email;
    private String telefon;
    private Adresa adresa;
    //private Cont[] conturi;

    public Client(int idClient, String nume, String prenume, String CNP, Date dataNastere, String email, String telefon, Adresa adresa) {
        this.idClient = idClient;
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
        return "ID client: " + idClient + "\n" +
                "Nume: " + nume + "\n" +
                "Prenume: " + prenume + "\n" +
                "CNP: " + CNP + "\n" +
                "Data nasterii: " + dataNastere + "\n" +
                "Email: " + email + "\n" +
                "Telefon='" + telefon + "\n" +
                "Adresa:" + adresa + "\n";
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

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public Date getDataNastere() {
        return dataNastere;
    }

    public void setDataNastere(Date dataNastere) {
        this.dataNastere = dataNastere;
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
}
