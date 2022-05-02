package aplicatieBancara.Entitati.Cont;

import aplicatieBancara.Entitati.Banca;
import aplicatieBancara.Entitati.Card.*;
import aplicatieBancara.Entitati.Tranzactie;
import java.util.*;
import java.util.ArrayList;


public class Cont {

    private String IBAN;
    private final String swift;
    private double sold;
    private String numeTitular;
    private int idClient;
    private ArrayList<Tranzactie> tranzactii = new ArrayList<>();

    private final GeneratorCard generatorCard = new GeneratorCard();


    public void adaugaTranzactie(Tranzactie tranzactie) {
        tranzactii.add(tranzactie);
        Collections.sort(tranzactii, new ComparatorTranzactii());
    }


    public Cont(String numeTitular, int idClient, Banca banca){
        this.IBAN = generareIBAN(idClient, banca);
        //this.sold = 0;
        this.swift = banca.getCodSwift();
        this.numeTitular = numeTitular;
        this.idClient = idClient;
    }

    public Cont(String IBAN, String numeTitular, int idClient, Banca banca){
        this.IBAN = IBAN;
        //this.sold = 0;
        this.swift = banca.getCodSwift();
        this.numeTitular = numeTitular;
        this.idClient = idClient;
    }

    private static String generareIBAN(int idClient, Banca banca){
        String bank = banca.getPrefixIBAN();
        Random rand = new Random();
        return bank + idClient + rand.nextInt(100000000, 1000000000);
    }

    public void actualizareSold(double suma){
        sold += suma;
    }

    public String getIBAN() {
        return IBAN;
    }

    public String getSwift() {
        return swift;
    }

    public double getSold() {
        return sold;
    }

    public String getNumeTitular() {
        return numeTitular;
    }

    public void setNumeTitular(String numeTitular) {
        this.numeTitular = numeTitular;
    }

    public int getIdClient() {
        return idClient;
    }

    public ArrayList<Tranzactie> getTranzactii() {
        return tranzactii;
    }

    public String toCSV()
    {
        String nume = numeTitular.split(" ")[0];
        String prenume = numeTitular.split(" ")[1];
        return this.idClient + "," + nume + "," + prenume + "," + this.IBAN + "," + this.sold;
    }

    @Override
    public String toString() {
        return "Cont{" +
                "IBAN='" + IBAN + '\'' +
                ", swift='" + swift + '\'' +
                ", sold=" + sold +
                ", nume titular='" + numeTitular + '\'' +
                ", idClient=" + idClient +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cont cont = (Cont) o;
        return Double.compare(cont.sold, sold) == 0 && idClient == cont.idClient && IBAN.equals(cont.IBAN) && swift.equals(cont.swift) && numeTitular.equals(cont.numeTitular) && tranzactii.equals(cont.tranzactii) && generatorCard.equals(cont.generatorCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IBAN, swift, sold, numeTitular, idClient, tranzactii, generatorCard);
    }
}
